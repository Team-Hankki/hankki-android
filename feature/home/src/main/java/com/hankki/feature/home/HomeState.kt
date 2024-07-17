package com.hankki.feature.home

import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.MarkerItem
import com.hankki.feature.home.model.PinModel
import com.hankki.feature.home.model.StoreItemModel
import com.hankki.feature.home.model.UniversityModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val myUniversityModel: UniversityModel = UniversityModel(
        name = null,
        latitude = 37.3009489417651,
        longitude = 127.03549529577874
    ),
    val categoryChipState: ChipState = ChipState.Unselected(),
    val categoryChipItems: PersistentList<CategoryChipItem> = persistentListOf(),
    val isCategoryChipOpen: Boolean = false,
    val priceChipState: ChipState = ChipState.Unselected(),
    val priceChipItems: PersistentList<ChipItem> = persistentListOf(),
    val isPriceChipOpen: Boolean = false,
    val sortChipState: ChipState = ChipState.Unselected(),
    val sortChipItems: PersistentList<ChipItem> = persistentListOf(),
    val isSortChipOpen: Boolean = false,
    val isMainBottomSheetOpen: Boolean = true,
    val isMyJogboBottomSheetOpen: Boolean = false,
    val selectedStoreItem: StoreItemModel = StoreItemModel(),
    val markerItems: PersistentList<PinModel> = persistentListOf(),
    val storeItems: PersistentList<StoreItemModel> = persistentListOf(),
    val jogboItems: PersistentList<JogboItemEntity> = persistentListOf(),
)
