package com.hankki.data.my.dto.request

import com.hankki.domain.my.entity.request.NewJogboEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewJogboDto(
    @SerialName("title")
    val title: String,
    @SerialName("details")
    val details: List<String>
)

fun NewJogboEntity.toDto() = NewJogboDto(
    title = title,
    details = details
)
