package com.hankki.core.network.data.response

import com.hankki.core.network.domain.entity.ReissueTokenResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReissueTokenResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
) {
    fun toReissueTokenResponseEntity() =
        ReissueTokenResponseEntity(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
}
