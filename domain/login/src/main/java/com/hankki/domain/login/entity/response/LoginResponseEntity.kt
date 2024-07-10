package com.hankki.domain.login.entity.response

data class LoginResponseEntity(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean
)
