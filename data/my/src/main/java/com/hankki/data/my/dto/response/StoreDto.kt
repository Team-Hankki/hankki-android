package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.StoreEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDto(
    @SerialName("stores")
    val stores: List<Store>
) {
    @Serializable
    data class Store(
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("imageUrl")
        val imageUrl: String,
        @SerialName("category")
        val category: String,
        @SerialName("lowestPrice")
        val lowestPrice: Int,
        @SerialName("heartCount")
        val heartCount: Int,
    )
}
fun StoreDto.toEntity() = stores.map {
    StoreEntity(
        category = it.category,
        heartCount = it.heartCount,
        id = it.id,
        imageURL = it.imageUrl,
        lowestPrice = it.lowestPrice,
        name = it.name,
        isLiked = true
    )
}
