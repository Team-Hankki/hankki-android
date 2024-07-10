package com.hankki.data.login.dto.response

import com.hankki.domain.login.entity.response.LoginResponseEntity
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
    fun toLoginEntity() =
        LoginResponseEntity(
            accessToken = accessToken,
            refreshToken = refreshToken,
            isRegistered = isRegistered
        )
}
