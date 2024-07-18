package com.hankki.data.home.dto.response

import com.hankki.domain.home.entity.response.StoreEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreThumbnailResponseDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("lowestPrice")
    val lowestPrice: Int,
    @SerialName("heartCount")
    val heartCount: Int,
    @SerialName("imageUrl")
    val imageUrl: String,
) {
    fun toEntity() = StoreEntity(
        id = id,
        name = name,
        category = category,
        lowestPrice = lowestPrice,
        heartCount = heartCount,
        imageUrl = imageUrl
    )
}
