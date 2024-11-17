package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

data class MenuUiState(
    val name: String = "",
    val price: String = "",
    val isPriceError: Boolean = false,
    val showPriceWarning: Boolean = false
)