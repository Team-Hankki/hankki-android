package com.hankki.feature.my.myjogbodetail

import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.Store
import com.hankki.domain.my.entity.response.UserInformationEntity
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
