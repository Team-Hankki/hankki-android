package com.hankki.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.login.entity.request.LoginRequestModel
import com.hankki.domain.login.repository.LoginRepository
import com.hankki.domain.token.repository.TokenRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState>
        get() = _loginState

    private val _loginSideEffects = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffects: SharedFlow<LoginSideEffect>
        get() = _loginSideEffects

    fun startKakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleLoginResult(token, error, context)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleLoginResult(token, error, context)
            }
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?, context: Context) {
        viewModelScope.launch {
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    handleLoginError("로그인 취소")
                } else {
                    handleLoginError("카카오계정으로 로그인 실패: ${error.localizedMessage}")
                    startKakaoWebLogin(context)
                }
            } else if (token != null) {
                sendTokenToServer(token.accessToken)
            }
        }
    }

    private fun startKakaoWebLogin(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            handleLoginResult(token, error, context)
        }
    }

    private fun sendTokenToServer(
        accessToken: String,
        platform: String = KAKAO
    ) {
        viewModelScope.launch {
            loginRepository.postLogin(accessToken, LoginRequestModel(platform))
                .onSuccess { response ->
                    tokenRepository.setTokens(response.accessToken, response.refreshToken)

                    Log.d("LoginViewModel", "Access Token: ${response.accessToken}")
                    Log.d("LoginViewModel", "Refresh Token: ${response.refreshToken}")
                    Log.d("LoginViewModel", "isRegistered: ${response.isRegistered}")

                    _loginState.value = _loginState.value.copy(
                        isLoggedIn = response.isRegistered,
                        errorMessage = null
                    )
                    _loginSideEffects.emit(LoginSideEffect.LoginSuccess(response.accessToken))
                }.onFailure { throwable ->
                    val errorMessage = throwable.localizedMessage ?: "Unknown error"
                    handleLoginError(errorMessage)
                }
        }
    }

    fun clearToken() {
        tokenRepository.clearInfo()
    }

    private fun handleLoginError(errorMessage: String) {
        _loginState.value = _loginState.value.copy(
            isLoggedIn = false,
            errorMessage = errorMessage
        )
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.LoginError(errorMessage))
        }
    }

    companion object {
        const val KAKAO = "kakao"
    }
}