package com.hankki.feature.home

import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.MarkerItem
import com.hankki.feature.home.model.StoreItemEntity
import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val latLng: LatLng = LatLng(37.3009489417651, 127.03549529577874),
    val universityName: String = "한끼 대학교",
    val categoryChipState: ChipState = ChipState.UNSELECTED,
    val categoryChipItems: PersistentList<CategoryChipItem> = persistentListOf(),
    val isCategoryChipOpen: Boolean = false,
    val priceChipState: ChipState = ChipState.UNSELECTED,
    val priceChipItems: PersistentList<String> = persistentListOf(),
    val isPriceChipOpen: Boolean = false,
    val sortChipState: ChipState = ChipState.UNSELECTED,
    val sortChipItems: PersistentList<String> = persistentListOf(),
    val isSortChipOpen: Boolean = false,
    val isMainBottomSheetOpen: Boolean = true,
    val isMyJogboBottomSheetOpen: Boolean = false,
    val selectedStoreItem : StoreItemEntity = StoreItemEntity(),
    val markerItems: PersistentList<MarkerItem> = persistentListOf(),
    val storeItems: PersistentList<StoreItemEntity> = persistentListOf(),
    val jogboItems: PersistentList<JogboItemEntity> = persistentListOf(),
)
