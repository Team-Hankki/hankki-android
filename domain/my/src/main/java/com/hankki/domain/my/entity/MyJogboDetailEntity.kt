package com.hankki.domain.my.entity

data class MyJogboDetailEntity(
    val title: String,
    val tags: List<String>,
    val stores: List<Store>
)

data class Store(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val category: String,
    val lowestPrice: Int,
    val heartCount: Int
)
