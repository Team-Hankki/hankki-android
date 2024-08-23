package com.hankki.feature.my.newjogbo

data class NewJogboState(
    val title : String = "",
    val tags : String = "",
    val isButtonEnabled : Boolean = false,
    var errorDialogState: Boolean = false
)
