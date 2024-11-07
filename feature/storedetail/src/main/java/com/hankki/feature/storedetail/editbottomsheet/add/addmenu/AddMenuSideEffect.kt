package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

sealed interface AddMenuSideEffect {
    data object NavigateToSuccess : AddMenuSideEffect
    data class ShowError(val message: String) : AddMenuSideEffect
    data object NavigateBack : AddMenuSideEffect
}