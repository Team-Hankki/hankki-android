package com.hankki.feature.login

sealed class LoginSideEffect {
    data object StartLogin : LoginSideEffect()
    data class LoginSuccess(val accessToken: String) : LoginSideEffect()
    data class LoginError(val errorMessage: String) : LoginSideEffect()
}
