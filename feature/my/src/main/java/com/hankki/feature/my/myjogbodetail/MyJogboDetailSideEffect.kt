package com.hankki.feature.my.myjogbodetail

sealed class MyJogboDetailSideEffect {
    data class NavigateToDetail(val id: Long) : MyJogboDetailSideEffect()
    data object NavigateToHome : MyJogboDetailSideEffect()
    data object ShowNoExistsDialog : MyJogboDetailSideEffect()
}
