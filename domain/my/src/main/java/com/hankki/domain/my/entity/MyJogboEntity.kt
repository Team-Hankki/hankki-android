package com.hankki.domain.my.entity

data class MyJogboEntity(
    val jogboId: Long,
    val jogboImage : String,
    val jogboName: String,
    var isSelected: Boolean = false
)
