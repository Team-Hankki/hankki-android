package com.hankki.feature.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState>
        get() = _loginState

    private val _loginSideEffects = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffects: SharedFlow<LoginSideEffect> = _loginSideEffects

    fun initLoginButton(context: Context) {
        viewModelScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    handleLoginResult(token, error)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    handleLoginResult(token, error)
                }
            }
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    handleLoginError("로그인 취소")
                } else {
                    handleLoginError("카카오계정으로 로그인 실패: ${error.localizedMessage}")
                }
            } else if (token != null) {
                handleLoginSuccess(token.accessToken)
            }
        }
    }

    private fun handleLoginSuccess(accessToken: String) {
        _loginState.value = _loginState.value.copy(
            isLoggedIn = true,
            accessToken = accessToken,
            errorMessage = null
        )
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.LoginSuccess(accessToken))
        }
    }

    private fun handleLoginError(errorMessage: String) {
        _loginState.value = _loginState.value.copy(
            isLoggedIn = false,
            accessToken = null,
            errorMessage = errorMessage
        )
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.LoginError(errorMessage))
        }
    }
}