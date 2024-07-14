package com.hankki.feature.my

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.hankki.domain.my.entity.MyJogboDetailEntity
import com.hankki.domain.my.entity.Store
import com.hankki.domain.my.entity.UserInformationEntity

data class MyJogboDetailState(
    val userInformation : UserInformationEntity = UserInformationEntity(
        nickname = "",
        profileImageUrl = ""
    ),
    val myStoreItems: MyJogboDetailEntity = MyJogboDetailEntity(
        title = "",
        tags = listOf("", ""),
        stores = listOf(
            Store(0, "", "", "", 0, 0)
        )

    ),
    val showDeleteDialog: MutableState<Boolean> = mutableStateOf(false),
    val showShareDialog: MutableState<Boolean> = mutableStateOf(false)
)
