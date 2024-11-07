package com.hankki.feature.storedetail.editbottomsheet.edit.mod

sealed class ModSideEffect {
    data object NavigateUp : ModSideEffect()
    data class NavigateToEditSuccess(val storeId: Long) : ModSideEffect()
    data class NavigateToDeleteSuccess(val storeId: Long) : ModSideEffect()
    data class MenuAddFailure(val message: String) : ModSideEffect()
}