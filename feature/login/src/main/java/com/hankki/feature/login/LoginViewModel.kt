package com.hankki.feature.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginSuccess -> {
                _loginState.value = LoginState(
                    isLoggedIn = true,
                    accessToken = event.accessToken,
                    errorMessage = null
                )
            }

            is LoginEvent.LoginError -> {
                _loginState.value = LoginState(
                    isLoggedIn = false,
                    accessToken = null,
                    errorMessage = event.errorMessage
                )
            }
        }
    }

    fun loginWithKakaoTalk(context: Context) {
        viewModelScope.launch {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    handleEvent(LoginEvent.LoginError("카카오계정으로 로그인 실패"))
                } else if (token != null) {
                    handleEvent(LoginEvent.LoginSuccess(token.accessToken))
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            handleEvent(LoginEvent.LoginError("로그인 취소"))
                            return@loginWithKakaoTalk
                        }

                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        handleEvent(LoginEvent.LoginSuccess(token.accessToken))
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }
}