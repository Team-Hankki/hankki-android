package com.hankki.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph() {
    composable<Home> {
        HomeRoute()
    }
}

@Serializable
data object Home : Route
