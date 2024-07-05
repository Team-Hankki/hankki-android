package com.hankki.feature.home

import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.component.ChipState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

data class HomeState(
    val categoryChipState: ChipState = ChipState.UNSELECTED,
    val categoryChipItems: PersistentList<String> = emptyList<String>().toPersistentList(),
    val isCategoryChipOpen: Boolean = false,
    val priceChipState: ChipState = ChipState.UNSELECTED,
    val priceChipItems: PersistentList<String> = emptyList<String>().toPersistentList(),
    val isPriceChipOpen: Boolean = false,
    val sortChipState: ChipState = ChipState.UNSELECTED,
    val sortChipItems: PersistentList<String> = emptyList<String>().toPersistentList(),
    val isSortChipOpen: Boolean = false,
    val isMainBottomSheetOpen: Boolean = true,
    val isMyJogboBottomSheetOpen: Boolean = false,
    val jogboItems: PersistentList<JogboItemEntity> = emptyList<JogboItemEntity>().toPersistentList(),
)
