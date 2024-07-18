package com.hankki.feature.my.myjogbo.model

import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.feature.my.R

data class MyJogboModel(
    val jogboId: Long = 0,
    val jogboImage: Int = 0,
    val jogboName: String = "",
    var jogboSelected: Boolean = false
)

fun MyJogboEntity.toMyJogboModel() = MyJogboModel(
    jogboId = this.jogboId,
    jogboImage = transformImage(this.jogboImage),
    jogboName = this.jogboName
)

fun transformImage(imageType: String): Int {
    return when (imageType) {
        "TYPE_ONE" -> R.drawable.img_list_0
        "TYPE_TWO" -> R.drawable.img_list_1
        "TYPE_THREE" -> R.drawable.img_list_2
        else -> R.drawable.img_list_3
    }
}

