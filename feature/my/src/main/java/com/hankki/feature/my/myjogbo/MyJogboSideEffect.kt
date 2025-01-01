package com.hankki.feature.my.myjogbo

sealed class MyJogboSideEffect {
    data object ShowNoExistsDialog : MyJogboSideEffect()
}
