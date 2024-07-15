package com.hankki.feature.storedetail.model

data class MenuItem(
    val name: String,
    val price: Int
)

data class StoreDetail(
    val name: String,
    val category: String,
    var isLiked: Boolean,
    val heartCount: Int,
    val imageUrls: List<String>,
    val menus: List<MenuItem>
)
