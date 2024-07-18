package com.hankki.feature.storedetail

import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StoreState(
    val storeDetail: UiState<StoreDetailResponseEntity> = UiState.Loading,
    val isLiked: Boolean = false,
    val heartCount: Int = 0,
    val selectedIndex: Int = -1,
    val storeId: Long = 0,
    val buttonLabels: PersistentList<String> = persistentListOf(),
    val isOpenBottomSheet: Boolean = false,
    val jogboItems: PersistentList<JogboResponseModel> = persistentListOf(),
)
