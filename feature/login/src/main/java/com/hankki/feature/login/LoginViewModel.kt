package com.hankki.feature.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.login.entity.request.LoginRequestEntity
import com.hankki.domain.login.repository.LoginRepository
import com.hankki.domain.token.repository.TokenRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _loginSideEffects = MutableSharedFlow<LoginSideEffect>()
    val loginSideEffects: SharedFlow<LoginSideEffect>
        get() = _loginSideEffects

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                handleLoginError("로그인 취소")
            } else {
                handleLoginError("카카오계정으로 로그인 실패")
            }
        } else if (token != null) {
            sendTokenToServer(token.accessToken)
        }
    }

    fun startKakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context as Activity) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        handleLoginError("로그인 취소")
                        return@loginWithKakaoTalk
                    }
                    startKakaoWebLogin(context)
                } else if (token != null) {
                    sendTokenToServer(token.accessToken)
                }
            }
        } else {
            startKakaoWebLogin(context)
        }
    }

    private fun startKakaoWebLogin(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
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
                }.onFailure { throwable ->
                    val errorMessage = throwable.localizedMessage ?: "Unknown error"
                    handleLoginError(errorMessage)
                }
        }
    }

    private fun handleLoginError(errorMessage: String) {
        viewModelScope.launch {
            _loginSideEffects.emit(LoginSideEffect.LoginError(errorMessage))
        }
    }

    companion object {
        const val KAKAO = "KAKAO"
    }
}
