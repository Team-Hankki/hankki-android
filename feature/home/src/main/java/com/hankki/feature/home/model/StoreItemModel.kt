package com.hankki.feature.home.model

import com.hankki.domain.home.entity.response.StoreEntity

data class StoreItemModel(
    val id: Long = 0,
    val name: String = "",
    val category: String = "",
    val lowestPrice: Int = 0,
    val heartCount: Int = 0,
    val imageUrl: String? = null
)

fun StoreEntity.toModel() = StoreItemModel(
    id = id,
    imageUrl = imageUrl,
    category = category,
    name = name,
    lowestPrice = lowestPrice,
    heartCount = heartCount
)
