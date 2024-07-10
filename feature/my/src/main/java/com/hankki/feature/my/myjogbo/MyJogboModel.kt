package com.hankki.feature.my.myjogbo

import com.hankki.domain.my.entity.MyJogboEntity

data class MyJogboModel (
    val jogboId : Int,
    val jogboName : String,
    var jogboSelected : Boolean = false
)


fun MyJogboEntity.toMyJogboModel() = MyJogboModel(
    jogboId = this.jogboId,
    jogboName = this.jogboName
)