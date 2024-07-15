package com.hankki.feature.storedetail

import com.hankki.core.common.utill.UiState
import com.hankki.feature.storedetail.model.StoreDetail
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class StoreState(
    val storeDetail: UiState<StoreDetail> = UiState.Loading,
    val isLiked: Boolean = false,
    val heartCount: Int = 0,
    val selectedIndex: Int = -1,
    val buttonLabels: PersistentList<String> = persistentListOf()
)
