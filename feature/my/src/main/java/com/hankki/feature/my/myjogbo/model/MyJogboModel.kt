package com.hankki.feature.my.myjogbo.model

import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.feature.my.R

data class MyJogboModel(
    val jogboId: Long = 0,
    val jogboImage: String = "",
    val jogboName: String = "",
    var jogboSelected: Boolean = false
)

fun MyJogboEntity.toMyJogboModel() = MyJogboModel(
    jogboId = this.jogboId,
    jogboImage = this.jogboImage,
    jogboName = this.jogboName
)

fun transformImage(imageType: String): Int = when (imageType) {
    "TYPE_ONE" -> com.hankki.core.designsystem.R.drawable.ic_jogbo_type_one
    "TYPE_TWO" -> com.hankki.core.designsystem.R.drawable.ic_jogbo_type_two
    "TYPE_THREE" -> com.hankki.core.designsystem.R.drawable.ic_jogbo_type_three
    else -> com.hankki.core.designsystem.R.drawable.ic_jogbo_type_four
}

