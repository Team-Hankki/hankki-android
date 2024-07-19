package com.hankki.feature.my.mystore

sealed class MyStoreSideEffect {
    data class NavigateToDetail(val id: Long) : MyStoreSideEffect()
}
