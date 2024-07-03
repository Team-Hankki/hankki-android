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

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val _loginSideEffects = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffects: SharedFlow<LoginSideEffect> = _loginSideEffects

    fun updateState(event: LoginSideEffect) {
        _loginState.value = when (event) {
            is LoginSideEffect.LoginSuccess -> LoginState(
                isLoggedIn = true,
                accessToken = event.accessToken,
                errorMessage = null
            )

            is LoginSideEffect.LoginError -> LoginState(
                isLoggedIn = false,
                accessToken = null,
                errorMessage = event.errorMessage
            )

            else -> _loginState.value
        }
    }

    fun initLoginButton() {
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.StartLogin)
        }
    }

    fun loginWithKakaoTalk(context: Context) {
        viewModelScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    handleLoginResult(context, token, error)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    handleLoginResult(context, token, error)
                }
            }
        }
    }

    private fun handleLoginResult(context: Context, token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    _loginSideEffects.emit(LoginSideEffect.LoginError("로그인 취소"))
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                        processTokenOrError(token, error)
                    }
                }
            } else {
                processTokenOrError(token, null)
            }
        }
    }

    private fun processTokenOrError(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            if (error != null) {
                _loginSideEffects.emit(LoginSideEffect.LoginError("카카오계정으로 로그인 실패: ${error.localizedMessage}"))
            } else if (token != null) {
                _loginSideEffects.emit(LoginSideEffect.LoginSuccess(token.accessToken))
            }
        }
    }
}