package com.hankki.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hankki.core.designsystem.theme.HankkijogboTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()

            HankkijogboTheme {
                MainScreen(navigator = navigator)
            }
        }
    }
}
