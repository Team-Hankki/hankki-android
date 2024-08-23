package com.hankki.feature.report.searchstore

import com.hankki.core.common.utill.EmptyUiState
import com.hankki.feature.report.model.LocationModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SearchStoreState(
    val universityId: Long = 1,
    val selectedLocation: LocationModel = LocationModel(),
    val isOpenDialog: Boolean = false,
    val dialogState: DialogState = DialogState.None,
    val uiState: EmptyUiState<PersistentList<LocationModel>> = EmptyUiState.Success(persistentListOf()),
)

sealed class DialogState {
    data object None : DialogState()
    class MySchool(val storeId: Long) : DialogState()
    class OtherSchool(val storeId: Long) : DialogState()
}
