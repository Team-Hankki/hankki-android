package com.hankki.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.main.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        setContent {
            var showSplash by remember { mutableStateOf(true) }
            val navigator: MainNavigator = rememberMainNavigator()

            HankkijogboTheme {
                LaunchedEffect(Unit) {
                    delay(SPLASH_SCREEN_DELAY)
                    showSplash = false
                }
                if (showSplash) {
                    // TODO: 내 대학 API 호출
                    SplashScreen()
                } else {
                    MainScreen(navigator = navigator)
                }
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 1200L
        // TODO: 수정 필요. 임시로 설정한 시간
    }
}
