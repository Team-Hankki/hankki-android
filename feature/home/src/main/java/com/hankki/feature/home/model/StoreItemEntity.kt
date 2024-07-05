package com.hankki.feature.home.model

data class StoreItemEntity(
    val storeImageUrl: String,
    val category: String,
    val storeName: String,
    val price: Int,
    val heartCount: Int,
)
