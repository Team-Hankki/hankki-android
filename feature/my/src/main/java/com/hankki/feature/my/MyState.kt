package com.hankki.feature.my

import com.hankki.domain.my.entity.UserInfoEntity

data class MyState(
    val userState : UserInfoEntity = UserInfoEntity("","")
)