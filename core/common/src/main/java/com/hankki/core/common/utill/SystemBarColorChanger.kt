package com.hankki.core.common.utill

import android.app.Activity
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

@Composable
fun SystemBarColorChanger(view: View, color: Color, shouldRollBack: Boolean = true) {
    if (!view.isInEditMode) {
        DisposableEffect(Unit) {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = color.toArgb()
            window.navigationBarColor = color.toArgb()

            onDispose {
                if (shouldRollBack) {
                    // 원래 상태로 복원
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                    window.statusBarColor = Color.White.toArgb()
                    window.navigationBarColor = Color.White.toArgb()
                }
            }
        }
    }
}
