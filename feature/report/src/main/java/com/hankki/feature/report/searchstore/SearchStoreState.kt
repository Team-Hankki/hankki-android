package com.hankki.feature.report.searchstore

import com.hankki.core.common.utill.EmptyUiState
import com.hankki.feature.report.model.LocationModel
import kotlinx.collections.immutable.PersistentList

data class SearchStoreState(
    val universityId: Long = 1,
    val selectedLocation: LocationModel = LocationModel(),
    val isOpenDialog: Boolean = false,
    val uiState: EmptyUiState<PersistentList<LocationModel>> = EmptyUiState.Loading
)
