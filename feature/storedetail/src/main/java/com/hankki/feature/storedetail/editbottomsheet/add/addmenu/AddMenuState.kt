package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class AddMenuState(
    val menuList: PersistentList<MenuUiState> = persistentListOf(MenuUiState()),
    val buttonEnabled: Boolean = false,
    val storeId: Long = 0L
)
