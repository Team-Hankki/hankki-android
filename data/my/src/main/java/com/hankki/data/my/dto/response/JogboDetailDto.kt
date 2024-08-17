package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.Store
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JogboDetailDto(
    @SerialName("title")
    val title: String,
    @SerialName("details")
    val details: List<String>,
    @SerialName("stores")
    val stores: List<Jogbo>,
) {
    @Serializable
    data class Jogbo(
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("imageUrl")
        val imageUrl: String?,
        @SerialName("category")
        val category: String,
        @SerialName("lowestPrice")
        val lowestPrice: Int,
        @SerialName("heartCount")
        val heartCount: Int,
    )
}

fun JogboDetailDto.toEntity() = MyJogboDetailEntity(
    title = this.title,
    chips = this.details,
    stores = stores.map {
        Store(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl,
            category = it.category,
            lowestPrice = it.lowestPrice,
            heartCount = it.heartCount
        )
    }
)
