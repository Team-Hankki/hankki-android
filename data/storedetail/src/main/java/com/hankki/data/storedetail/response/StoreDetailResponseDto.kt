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
    val menus: List<MenuItemDto>
) {
    fun toStoreDetailResponseEntity() = StoreDetailResponseEntity(
        name = name,
        category = category,
        isLiked = isLiked,
        heartCount = heartCount,
        imageUrls = imageUrls,
        menus = menus.map { it.toMenuItem() }
    )
}

@Serializable
data class MenuItemDto(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int
) {
    fun toMenuItem() = MenuItem(
        name = name,
        price = price
    )
}
