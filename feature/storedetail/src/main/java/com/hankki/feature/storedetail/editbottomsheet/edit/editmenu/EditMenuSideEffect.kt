
package com.hankki.feature.storedetail.editbottomsheet.edit.editmenu

sealed interface EditMenuSideEffect {
    data object NavigateBack : EditMenuSideEffect
    data class NavigateToDeleteSuccess(val storeId: Long) : EditMenuSideEffect
    data class ShowSnackbar(val message: String) : EditMenuSideEffect
}
