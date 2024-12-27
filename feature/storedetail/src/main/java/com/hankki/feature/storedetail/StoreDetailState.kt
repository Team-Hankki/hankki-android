package com.hankki.feature.storedetail

import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.feature.storedetail.editbottomsheet.add.addmenu.MenuUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StoreDetailState(
    val storeDetail: UiState<StoreDetailResponseEntity> = UiState.Loading,
    val isLiked: Boolean = false,
    val heartCount: Int = 0,
    val selectedIndex: Int = -1,
    val storeId: Long = 0,
    val buttonLabels: PersistentList<String> = persistentListOf(),
    val isOpenJogboBottomSheet: Boolean = false,
    val jogboItems: PersistentList<JogboResponseModel> = persistentListOf(),
    val nickname: String = "",
    val isOpenEditMenuBottomSheet: Boolean = false,
    val menuItems: PersistentList<MenuItem> = persistentListOf(),
    val menuList: List<MenuUiState> = listOf(MenuUiState()),
    val buttonEnabled: Boolean = false,
    val submittedMenuCount: Int = 0,
    val errorMessage: String = "",
    val categoryImageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
