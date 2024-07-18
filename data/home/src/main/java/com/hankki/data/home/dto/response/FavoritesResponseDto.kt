package com.hankki.data.home.dto.response

import com.hankki.domain.home.entity.response.JogboResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoritesResponseDto(
    @SerialName("favorites")
    val favorites: List<JogboResponseDto>
) {
    @Serializable
    data class JogboResponseDto(
        @SerialName("id")
        val id: Long,
        @SerialName("title")
        val title: String,
        @SerialName("imageType")
        val imageType: String,
        @SerialName("details")
        val details: List<String>,
        @SerialName("isAdded")
        val isAdded: Boolean
    ){
        fun toEntity() = JogboResponseEntity(
            id = id,
            title = title,
            imageType = imageType,
            details = details,
            isAdded = isAdded
        )
    }

    fun toEntity() = favorites.map { it.toEntity() }
}
