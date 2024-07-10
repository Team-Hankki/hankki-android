package com.hankki.feature.my.myjogbo.model

import com.hankki.domain.my.entity.MyJogboEntity

data class MyJogboModel (
    val jogboId : Long,
    val jogboName : String,
    var jogboSelected : Boolean = false
)


fun MyJogboEntity.toMyJogboModel() = MyJogboModel(
    jogboId = this.jogboId,
    jogboName = this.jogboName
)
