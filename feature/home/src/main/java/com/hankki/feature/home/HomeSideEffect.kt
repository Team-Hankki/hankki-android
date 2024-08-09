package com.hankki.feature.home

sealed class HomeSideEffect {
    data class ShowSnackBar(val message: String, val jogboId: Long) : HomeSideEffect()
    data class MoveMap(val latitude: Double, val longitude: Double) : HomeSideEffect()
}
