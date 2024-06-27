package com.hankki.feature.dummy

import androidx.annotation.StringRes

sealed class DummySideEffect {
    data class SnackBar(val message: String) : DummySideEffect()
}
