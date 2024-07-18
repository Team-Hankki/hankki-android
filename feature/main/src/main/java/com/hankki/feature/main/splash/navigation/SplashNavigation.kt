package com.hankki.feature.main.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.main.splash.SplashScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.splashNavGraph(
    navigateToHome: (Boolean) -> Unit,
    navigateToLogIn: () -> Unit,
) {
    composable<Splash> {
        SplashScreen(
            navigateToHome = navigateToHome,
            navigateToLogIn = navigateToLogIn
        )
    }
}

@Serializable
data object Splash : Route
