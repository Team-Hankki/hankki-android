package com.hankki.data.login.dto.response

import com.hankki.domain.login.entity.response.LoginResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isRegistered")
    val isRegistered: Boolean
) {
    fun toLoginModel() =
        LoginResponseModel(
            accessToken = accessToken,
            refreshToken = refreshToken,
            isRegistered = isRegistered
        )
}
