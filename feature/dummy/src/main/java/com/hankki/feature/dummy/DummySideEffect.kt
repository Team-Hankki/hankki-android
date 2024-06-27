package com.hankki.feature.dummy

sealed class DummySideEffect {
    data class SnackBar(val message: String) : DummySideEffect()
}
