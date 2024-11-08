package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

sealed class AddMenuEvent {
    data class OnMenuNameChange(val index: Int, val name: String) : AddMenuEvent()
    data class OnPriceChange(val index: Int, val price: String) : AddMenuEvent()
    data class OnDeleteMenu(val index: Int) : AddMenuEvent()
    data object OnAddMenu : AddMenuEvent()
    data object OnSubmit : AddMenuEvent()
    data object OnBackClick : AddMenuEvent()
}