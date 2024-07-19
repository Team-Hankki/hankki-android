package com.hankki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.login.OnboardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateOnboarding(navOptions: NavOptions) {
    navigate(Onboarding, navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(navigateToUniversity: () -> Unit) {
    composable<Onboarding> {
        OnboardingRoute(
            navigateToUniversity = navigateToUniversity
        )
    }
}

@Serializable
data object Onboarding : Route
