package com.hankki.feature.my.myjogbodetail

import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.Store
import com.hankki.domain.my.entity.response.UserInformationEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MyJogboDetailState(
    val userInformation: UserInformationEntity = UserInformationEntity(
        nickname = ""
    ),
    val myStoreItems: MyJogboDetailEntity = MyJogboDetailEntity(
        title = "",
        chips = persistentListOf("", ""),
        stores = persistentListOf(
            Store(0, "", "", "", 0, 0)
        )
    ),
    val uiState: EmptyUiState<PersistentList<Store>> = EmptyUiState.Loading,
    var deleteDialogState: Boolean = false,
    var shareDialogState: Boolean = false,
    var selectedStoreId : Long = 0
)
