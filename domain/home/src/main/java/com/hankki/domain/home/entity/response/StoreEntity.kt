package com.hankki.domain.home.entity.response

data class StoreEntity(
    val id: Long,
    val imageUrl: String?,
    val category: String,
    val name: String,
    val lowestPrice: Int,
    val heartCount: Int
)
