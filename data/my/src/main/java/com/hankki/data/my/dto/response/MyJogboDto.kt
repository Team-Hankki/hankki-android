package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.MyJogboEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyJogboDto(
    @SerialName("favorites")
    val favorites: List<Jogbo>
) {
    @Serializable
    data class Jogbo(
        @SerialName("id")
        val id: Long,
        @SerialName("title")
        val title: String,
        @SerialName("imageType")
        val imageType: String,
    )
}

fun MyJogboDto.toEntity() = favorites.map {
    MyJogboEntity(
        jogboId = it.id,
        jogboName = it.title,
        jogboImage = it.imageType
    )
}
