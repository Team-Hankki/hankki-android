package com.hankki.data.storedetail.response

import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailResponseDto(
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("heartCount")
    val heartCount: Int,
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("menus")
    val menus: List<MenuItemDto>,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("categoryImageUrl")
    val categoryImageUrl: String
) {
    fun toStoreDetailResponseEntity() = StoreDetailResponseEntity(
        name = name,
        category = category,
        isLiked = isLiked,
        heartCount = heartCount,
        imageUrls = imageUrls,
        menus = menus.map { it.toMenuItem() },
        latitude = latitude,
        longitude = longitude,
        categoryImageUrl = categoryImageUrl
    )
}

@Serializable
data class MenuItemDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int
) {
    fun toMenuItem() = MenuItem(
        id = id,
        name = name,
        price = price
    )
}
