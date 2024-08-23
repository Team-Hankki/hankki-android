package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.response.UserInfoResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseDto(
    @SerialName("nickname")
    val nickname: String
) {
    fun toEntity() = UserInfoResponseEntity(
        nickname = nickname
    )
}
