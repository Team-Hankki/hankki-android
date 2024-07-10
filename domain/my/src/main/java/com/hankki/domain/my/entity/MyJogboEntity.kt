package com.hankki.domain.my.entity

data class MyJogboEntity(
    val jogboId : Int,
    val jogboName: String ,
    var isSelected: Boolean = false
)