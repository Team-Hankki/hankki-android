package com.hankki.feature.my.myjogbodetail

sealed class MyJogboSideEffect {
    data class NavigateToDetail(val id: Long) : MyJogboSideEffect()
}
