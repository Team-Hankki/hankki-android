package com.hankki.core.designsystem.event

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarTrigger = staticCompositionLocalOf<(String) -> Unit> {
    error("No SnackBar provided")
}

val LocalButtonSnackBarTrigger = staticCompositionLocalOf<(String, Long) -> Unit> {
    error("No ButtonSnackBar provided")
}

val LocalWhiteSnackBarTrigger = staticCompositionLocalOf<(String, Long) -> Unit> {
    error("No WhiteSnackBar provided")
}
