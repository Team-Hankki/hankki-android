package com.hankki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.login.OnboardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOnboarding() {
    navigate(Onboarding)
}

fun NavGraphBuilder.onboardingNavgraph(navigateToHome: () -> Unit) {
    composable<Onboarding> {
        OnboardingRoute(
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object Onboarding : Route