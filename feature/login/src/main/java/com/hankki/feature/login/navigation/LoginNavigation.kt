package com.hankki.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateLogin(isLoginDialogNeed: Boolean = false) {
    navigate(Login(isLoginDialogNeed))
}

fun NavGraphBuilder.loginNavGraph(
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    composable<Login> { backStackEntry ->
        val isLoginDialogNeed = backStackEntry.toRoute<Login>().isLoginDialogNeed
        LoginRoute(
            isLoginDialogNeed = isLoginDialogNeed,
            navigateToHome = navigateToHome,
            navigateToOnboarding = navigateToOnboarding
        )
    }
}

@Serializable
data class Login(
    val isLoginDialogNeed: Boolean
) : Route
