package com.hankki.feature.home

import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.PinModel
import com.hankki.feature.home.model.StoreItemModel
import com.hankki.feature.home.model.UniversityModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val isOpenDialog: Boolean = false,
    val myUniversityModel: UniversityModel = UniversityModel(
        latitude = 37.583639,
        longitude = 127.0588564
    ),
    val categoryChipState: ChipState = ChipState.Unselected(),
    val categoryChipItems: PersistentList<CategoryChipItem> = persistentListOf(),
    val isCategoryChipOpen: Boolean = false,
    val priceChipState: ChipState = defaultPriceChipState,
    val priceChipItems: PersistentList<ChipItem> = persistentListOf(),
    val isPriceChipOpen: Boolean = false,
    val sortChipState: ChipState = defaultSortChipState,
    val sortChipItems: PersistentList<ChipItem> = persistentListOf(),
    val isSortChipOpen: Boolean = false,
    val isMainBottomSheetOpen: Boolean = true,
    val isMyJogboBottomSheetOpen: Boolean = false,
    val isFilterBottomSheetOpen: Boolean = false,
    val selectedStoreItem: StoreItemModel? = null,
    val markerItems: PersistentList<PinModel> = persistentListOf(),
    val storeItems: EmptyUiState<PersistentList<StoreItemModel>> = EmptyUiState.Loading,
    val jogboItems: PersistentList<JogboResponseModel> = persistentListOf(),
)

val defaultPriceChipState = ChipState.Fixed(
    title = "전체",
    tag = "ALL"
)

val defaultSortChipState = ChipState.Fixed(
    title = "최신순",
    tag = "LATEST"
)
