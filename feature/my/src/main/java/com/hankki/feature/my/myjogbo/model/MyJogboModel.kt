package com.hankki.feature.my.myjogbo.model

import com.hankki.domain.my.entity.response.MyJogboEntity

data class MyJogboModel(
    val jogboId: Long,
    val jogboImage: String,
    val jogboName: String,
    var jogboSelected: Boolean = false
)


fun MyJogboEntity.toMyJogboModel() = MyJogboModel(
    jogboId = this.jogboId,
    jogboImage = this.jogboImage,
    jogboName = this.jogboName
)
