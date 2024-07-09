package com.hankki.domain.login.entity.response

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean
)