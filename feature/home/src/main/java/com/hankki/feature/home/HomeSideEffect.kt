package com.hankki.feature.home

sealed class HomeSideEffect {
    data class ShowSnackBar(val message: String) : HomeSideEffect()
    data class MoveMap(val latitude: Double, val longitude: Double) : HomeSideEffect()
}
