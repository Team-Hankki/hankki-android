package com.hankki.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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
fun ProvideSoptColorsAndTypography(typography: HankkiTypography, content: @Composable () -> Unit) {
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
    val colorScheme = LightColorScheme // 현재는 다크테마 미지원. 추후 지원시 수정 예정
    val typography = hankkiTypography()

    ProvideSoptColorsAndTypography(typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}
