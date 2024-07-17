package com.hankki.feature.my.mypage

import com.hankki.domain.my.entity.UserInformationEntity

data class MyState(
    val userState: UserInformationEntity = UserInformationEntity("", "")
)
