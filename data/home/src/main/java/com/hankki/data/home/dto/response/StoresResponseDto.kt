package com.hankki.data.home.dto.response

import com.hankki.domain.home.entity.response.StoreEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoresResponseDto(
    @SerialName("stores")
    val stores: List<StoreResponseDto>
){
    @Serializable
    data class StoreResponseDto(
        @SerialName("id")
        val id: Long,
        @SerialName("imageUrl")
        val imageUrl: String?,
        @SerialName("category")
        val category: String,
        @SerialName("name")
        val name: String,
        @SerialName("lowestPrice")
        val lowestPrice: Int,
        @SerialName("heartCount")
        val heartCount: Int
    ){
        fun toEntity() = StoreEntity(
            id = id,
            imageUrl = imageUrl,
            category = category,
            name = name,
            lowestPrice = lowestPrice,
            heartCount = heartCount
        )
    }
    fun toEntity() = stores.map { it.toEntity() }
}
