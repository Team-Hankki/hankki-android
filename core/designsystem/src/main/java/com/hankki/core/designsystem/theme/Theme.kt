package com.hankki.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = lightColorScheme(
    primary = White,
    surfaceTint = White,
    background = White,
    onBackground = White
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    surfaceTint = White,
    background = White,
    onBackground = White
)

private val LocalHankkiTypography = staticCompositionLocalOf<HankkiTypography> {
    error("No HankkiTypography provided")
}

/* HankkiTheme
*
* Typo를 변경하고 싶다면 HankkiTheme.typography.h1으로 접근하시면 됩니다.
* ex) Text(text = "Hankki Example Typo", style = HankkiTheme.typography.h1)
*/
object HankkiTheme {
    val typography: HankkiTypography
        @Composable get() = LocalHankkiTypography.current
}

@Composable
fun ProvideHankkiTypography(typography: HankkiTypography, content: @Composable () -> Unit) {
    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)
    CompositionLocalProvider(
        LocalHankkiTypography provides provideTypography,
        content = content
    )
}

@Composable
fun HankkijogboTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val typography = hankkiTypography()

    // set status bar & navigation bar color
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            window.navigationBarColor = White.toArgb()

            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightNavigationBars = true
        }
    }

    ProvideHankkiTypography(typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
