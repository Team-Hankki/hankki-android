package com.hankki.feature.my.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.my.MyRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMy(navOptions: NavOptions) {
    navigate(My, navOptions)
}

fun NavGraphBuilder.myNavGraph(navigateToDummy: () -> Unit, navigateToLogin: () -> Unit) {
    composable<My> {
        MyRoute(navigateToDummy, navigateToLogin)
    }
}

@Serializable
data object My : MainTabRoute
