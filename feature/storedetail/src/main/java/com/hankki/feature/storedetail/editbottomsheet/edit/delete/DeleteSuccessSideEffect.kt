package com.hankki.feature.storedetail.editbottomsheet.edit.delete

sealed interface DeleteSuccessSideEffect {
    data object NavigateToEditMenu : DeleteSuccessSideEffect
    data object NavigateToStoreDetail : DeleteSuccessSideEffect
    data object NavigateUp : DeleteSuccessSideEffect
}