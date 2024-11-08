package com.hankki.feature.storedetail.editbottomsheet.edit.delete

sealed class DeleteSuccessSideEffect {
    data object NavigateToEditMenu : DeleteSuccessSideEffect()
    data object NavigateToStoreDetail : DeleteSuccessSideEffect()
}