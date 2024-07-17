package com.hankki.feature.home.model

import com.hankki.domain.home.entity.response.StoreEntity

data class StoreItemModel(
    val id: Long = 0,
    val imageUrl: String = "",
    val category: String = "",
    val name: String = "",
    val lowerPrice: Int = 0,
    val heartCount: Int = 0,
)

fun StoreEntity.toModel() = StoreItemModel(
    id = id,
    imageUrl = imageUrl,
    category = category,
    name = name,
    lowerPrice = lowestPrice,
    heartCount = heartCount
)
