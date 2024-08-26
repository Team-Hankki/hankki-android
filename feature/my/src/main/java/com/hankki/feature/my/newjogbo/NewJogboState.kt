package com.hankki.feature.my.newjogbo

data class NewJogboState(
    val title : String = "",
    val tags : String = "",
    val buttonEnabled : Boolean = false,
    var errorDialogState: Boolean = false
)
