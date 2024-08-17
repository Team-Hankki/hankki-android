package com.hankki.feature.my.mystore.model

import com.hankki.domain.my.entity.response.StoreEntity

data class MyStoreModel(
    val category: String = "",
    val heartCount: Int = 0,
    val id: Long = 0,
    val imageURL: String? = null,
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
