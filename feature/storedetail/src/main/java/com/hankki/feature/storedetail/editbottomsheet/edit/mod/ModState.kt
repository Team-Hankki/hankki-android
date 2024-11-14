package com.hankki.feature.storedetail.editbottomsheet.edit.mod

data class ModState(
    val menuName: String = "",
    val price: String = "",
    val isPriceValid: Boolean = true,
    val isOverPriceLimit: Boolean = false,
    val showRestoreMenuNameButton: Boolean = false,
    val showRestorePriceButton: Boolean = false,
    val isMenuFieldFocused: Boolean = false,
    val isPriceFieldFocused: Boolean = false,
    val isSubmitEnabled: Boolean = false,
)
