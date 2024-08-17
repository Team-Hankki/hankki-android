package com.hankki.feature.my.mypage.model

import com.hankki.domain.my.entity.response.UserInformationEntity

data class MyModel(
    val nickname: String = ""
)

fun UserInformationEntity.toModel() = MyModel(
    nickname = nickname
)
