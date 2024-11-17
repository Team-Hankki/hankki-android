package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

sealed class AddMenuIntent {
    data class OnMenuNameChange(val index: Int, val name: String) : AddMenuIntent()
    data class OnPriceChange(val index: Int, val price: String) : AddMenuIntent()
    data class OnDeleteMenu(val index: Int) : AddMenuIntent()
    data object OnAddMenu : AddMenuIntent()
    data object OnSubmit : AddMenuIntent()
    data object OnBackClick : AddMenuIntent()
}