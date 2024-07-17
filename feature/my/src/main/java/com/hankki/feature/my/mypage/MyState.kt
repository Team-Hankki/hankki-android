package com.hankki.feature.my.mypage

import com.hankki.domain.my.entity.response.UserInformationEntity

data class MyState(
    val userState: UserInformationEntity = UserInformationEntity("", "")
)
