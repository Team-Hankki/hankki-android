package com.hankki.feature.my.myjogbodetail

import com.hankki.domain.my.entity.MyJogboDetailEntity
import com.hankki.domain.my.entity.Store
import com.hankki.domain.my.entity.UserInformationEntity
import kotlinx.collections.immutable.persistentListOf

data class MyJogboDetailState(
    val userInformation: UserInformationEntity = UserInformationEntity(
        nickname = "",
        profileImageUrl = ""
    ),
    val myStoreItems: MyJogboDetailEntity = MyJogboDetailEntity(
        title = "",
        chips = persistentListOf("", ""),
        stores = persistentListOf(
            Store(0, "", "", "", 0, 0)
        )
    ),
    var showDeleteDialog: Boolean = false,
    var showShareDialog: Boolean = false
)
