package com.hankki.domain.my.entity.response

data class StoreEntity(
    val category: String,
    val heartCount: Int,
    val id: Long,
    val imageURL: String?,
    val isLiked: Boolean?,
    val lowestPrice: Int,
    val name: String
)
