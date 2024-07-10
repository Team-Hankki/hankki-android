package com.hankki.domain.my.entity

data class MyJogboEntity(
    val jogboId: Long,
    val jogboName: String,
    var isSelected: Boolean = false
)