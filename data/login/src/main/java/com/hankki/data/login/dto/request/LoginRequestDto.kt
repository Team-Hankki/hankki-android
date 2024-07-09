package com.hankki.data.login.dto.request

import com.hankki.domain.login.entity.request.LoginRequestModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("platform")
    val platform: String
)

fun LoginRequestModel.toLoginRequestDto(): LoginRequestDto =
    LoginRequestDto(platform)
