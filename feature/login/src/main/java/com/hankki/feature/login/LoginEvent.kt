package com.hankki.feature.login

sealed class LoginEvent {
    data class LoginSuccess(val accessToken: String) : LoginEvent()
    data class LoginError(val errorMessage: String) : LoginEvent()
}