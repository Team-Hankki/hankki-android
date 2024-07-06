package com.hankki.feature.home

import androidx.annotation.StringRes

sealed class HomeSideEffect {
    data class SnackBar(@StringRes val message: Int) : HomeSideEffect()
    data class MoveMap(val latitude: Double, val longitude: Double) : HomeSideEffect()
}