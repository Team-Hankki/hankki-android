package com.hankki.feature.login

sealed class LoginSideEffect {
    data class LoginSuccess(val accessToken: String, val isRegistered: Boolean) : LoginSideEffect()
    data class LoginError(val errorMessage: String) : LoginSideEffect()
}
