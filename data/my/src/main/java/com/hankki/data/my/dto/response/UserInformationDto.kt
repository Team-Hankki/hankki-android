package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.UserInformationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInformationDto(
    @SerialName("nickname")
    val nickname: String
) {
    fun toEntity() = UserInformationEntity(
        nickname = nickname
    )
}
