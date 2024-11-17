package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

sealed class AddMenuSideEffect {
    data object NavigateToSuccess : AddMenuSideEffect()
    data class ShowError(val message: String) : AddMenuSideEffect()
    data object NavigateBack : AddMenuSideEffect()
}