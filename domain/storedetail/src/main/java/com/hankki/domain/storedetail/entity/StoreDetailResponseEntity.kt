package com.hankki.domain.storedetail.entity

data class StoreDetailResponseEntity(
    val name: String,
    val category: String,
    var isLiked: Boolean,
    val heartCount: Int,
    val imageUrls: List<String>,
    val menus: List<MenuItem>
)

data class MenuItem(
    val id: Long,
    val name: String,
    val price: Int
)
