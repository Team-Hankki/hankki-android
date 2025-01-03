package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.IsJogboOwnerEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class IsJogboOwnerResponseDto(
    @SerialName("isOwner")
    val isJogboOwner: Boolean
)

fun IsJogboOwnerResponseDto.toEntity() = IsJogboOwnerEntity(
    isJogboOwner = this.isJogboOwner
)
