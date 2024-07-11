package com.hankki.domain.my.entity

data class StoreEntity(
    val category: String,
    val heartCount: Int,
    val id: Int,
    val imageURL: String,
    val isLiked: Boolean,
    val lowestPrice: Int,
    val name: String
)