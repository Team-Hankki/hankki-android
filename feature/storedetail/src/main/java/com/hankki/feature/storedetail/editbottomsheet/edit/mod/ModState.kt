package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.compose.ui.text.input.TextFieldValue

data class ModState(
    val menuNameFieldValue: TextFieldValue = TextFieldValue(),
    val priceFieldValue: TextFieldValue = TextFieldValue(),
    val isPriceValid: Boolean = true,
    val isOverPriceLimit: Boolean = false,
    val showRestoreMenuNameButton: Boolean = false,
    val showRestorePriceButton: Boolean = false
)