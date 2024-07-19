package com.hankki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateLogin() {
    navigate(Login)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    composable<Login> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToOnboarding = navigateToOnboarding
        )
    }
}

@Serializable
data object Login : Route
