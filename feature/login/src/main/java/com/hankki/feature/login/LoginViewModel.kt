package com.hankki.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.login.entity.request.LoginRequestEntity
import com.hankki.domain.login.repository.LoginRepository
import com.hankki.domain.token.repository.TokenRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState>
        get() = _loginState.asStateFlow()

    private val _loginSideEffects = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffects: SharedFlow<LoginSideEffect>
        get() = _loginSideEffects

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    fun setButtonEnabled(enabled: Boolean) {
        _isButtonEnabled.value = enabled
    }

    fun startKakaoLogin(isKakaoTalkAvailable: Boolean) {
        viewModelScope.launch {
            if (isKakaoTalkAvailable) {
                _loginSideEffects.emit(LoginSideEffect.StartKakaoTalkLogin)
            } else {
                _loginSideEffects.emit(LoginSideEffect.StartKakaoWebLogin)
            }
        }
    }

    fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    _loginSideEffects.emit(LoginSideEffect.LoginError("로그인 취소"))
                } else {
                    _loginSideEffects.emit(LoginSideEffect.StartKakaoWebLogin)
                }
                setButtonEnabled(true)
            } else if (token != null) {
                sendTokenToServer(token.accessToken)
            } else {
                setButtonEnabled(true)
            }
        }
    }

    private fun sendTokenToServer(
        accessToken: String,
        platform: String = KAKAO
    ) {
        viewModelScope.launch {
            loginRepository.postLogin(accessToken, LoginRequestEntity(platform))
                .onSuccess { response ->
                    tokenRepository.setTokens(response.accessToken, response.refreshToken)
                    _loginSideEffects.emit(
                        LoginSideEffect.LoginSuccess(
                            response.accessToken,
                            response.isRegistered
                        )
                    )
                    setButtonEnabled(false)
                }.onFailure { throwable ->
                    val errorMessage = throwable.localizedMessage ?: "Unknown error"
                    handleLoginError(errorMessage)
                }
        }
    }

    private fun handleLoginError(errorMessage: String) {
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.LoginError(errorMessage))
            setButtonEnabled(true)
        }
    }

    fun updateLoginDialog() {
        _loginState.value = _loginState.value.copy(
            loginDialogState = !_loginState.value.loginDialogState,
        )
    }

    companion object {
        const val KAKAO = "KAKAO"
    }
}
