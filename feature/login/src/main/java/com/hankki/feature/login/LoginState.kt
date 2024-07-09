package com.hankki.feature.login

data class LoginState(
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)