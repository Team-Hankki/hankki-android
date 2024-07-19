package com.hankki.data.my.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JogbosRequestDto(
    @SerialName("favoriteIds")
    val favoriteIds: List<Long>,
)

fun List<Long>.toDto() = JogbosRequestDto(
    favoriteIds = this
)
