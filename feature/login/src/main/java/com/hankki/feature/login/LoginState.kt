package com.hankki.feature.login

data class LoginState(
    val isLoggedIn: Boolean = false,
    val accessToken: String? = null,
    val errorMessage: String? = null
)