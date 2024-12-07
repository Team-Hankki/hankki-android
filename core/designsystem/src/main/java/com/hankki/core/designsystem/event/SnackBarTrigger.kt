package com.hankki.core.designsystem.event

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarTrigger = staticCompositionLocalOf<(String) -> Unit> {
    error("No SnackBar provided")
}

val LocalSnackBarWithButtonTrigger = staticCompositionLocalOf<(String, Long) -> Unit> {
    error("No SnackBar provided")
}


val LocalWhiteSnackBarTrigger = staticCompositionLocalOf<(String, Long) -> Unit> {
    error("No SnackBar provided")
}
