package com.hankki.feature.home

import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.StoreItemEntity
import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

data class HomeState(
    val latLng: LatLng = LatLng(37.3009489417651, 127.03549529577874),
    val universityName: String = "한끼 대학교",
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
    val storeItems: PersistentList<StoreItemEntity> = emptyList<StoreItemEntity>().toPersistentList(),
    val jogboItems: PersistentList<JogboItemEntity> = emptyList<JogboItemEntity>().toPersistentList(),
)