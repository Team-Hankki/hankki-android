package com.hankki.feature.my.mystore.model

import com.hankki.domain.my.entity.StoreEntity

data class MyStoreModel(
    val category: String = "",
    val heartCount: Int = 0,
    val id: Int = 0,
    val imageURL: String = "",
    val isLiked: Boolean? = null,
    val lowestPrice: Int = 0,
    val name: String = ""
)


fun StoreEntity.toMyStoreModel() = MyStoreModel(
    category = this.category,
    heartCount = this.heartCount,
    id = this.id,
    imageURL = this.imageURL,
    isLiked = this.isLiked,
    lowestPrice = this.lowestPrice,
    name = this.name
)
