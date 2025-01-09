package com.hankki.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hankki.core.common.amplitude.AmplitudeTracker
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.designsystem.theme.HankkijogboTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var tracker: AmplitudeTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMaterial3ScrollableTabRowMinWidthCustomer(this)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition { false }
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            val isDeepLink = rememberSaveable { mutableStateOf(intent.data?.host == "kakaolink") }

            HankkijogboTheme {
                CompositionLocalProvider(LocalTracker provides tracker) {
                    MainScreen(
                        navigator = navigator,
                        isDeepLink = isDeepLink.value,
                        resetDeepLinkState = { isDeepLink.value = false }
                    )
                }
            }
        }
    }
}

// Material의 ScrollableTabRow의 minWidth가 하드코딩 되어있어 커스텀이 불가능하여 강제로 수정.
fun setMaterial3ScrollableTabRowMinWidthCustomer(activity: ComponentActivity) {
    try {
        Class
            .forName("androidx.compose.material3.TabRowKt")
            .getDeclaredField("ScrollableTabRowMinimumTabWidth").apply {
                isAccessible = true
            }.set(activity, 0f)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
