package com.hankki.feature.my.mypage

import com.hankki.domain.my.entity.UserInfoEntity

data class MyState(
    val userState : UserInfoEntity = UserInfoEntity("","")
)