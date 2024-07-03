package com.hankki.feature.my.navigation

import androidx.compose.foundation.layout.PaddingValues
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

fun NavGraphBuilder.myNavGraph(
    paddingValues: PaddingValues,
    navigateToDummy: () -> Unit
) {
    composable<My> {
        MyRoute(paddingValues, navigateToDummy)
    }
}

@Serializable
data object My : MainTabRoute
