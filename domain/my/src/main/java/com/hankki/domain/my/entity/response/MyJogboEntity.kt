package com.hankki.domain.my.entity.response

data class MyJogboEntity(
    val jogboId: Long,
    val jogboImage : String,
    val jogboName: String,
    var isSelected: Boolean = false
)
