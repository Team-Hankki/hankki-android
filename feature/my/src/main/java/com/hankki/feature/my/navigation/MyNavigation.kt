package com.hankki.feature.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.my.myjogbo.MyJogboRoute
import com.hankki.feature.my.mypage.MyRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMy(navOptions: NavOptions) {
    navigate(My, navOptions)
}

fun NavController.navigateMyJogbo() {
    navigate(MyJogbo)
}

fun NavGraphBuilder.myNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMyJogbo: () -> Unit
) {
    composable<My> {
        MyRoute(paddingValues, navigateToMyJogbo)
    }
    composable<MyJogbo> {
        MyJogboRoute(paddingValues,navigateUp)
    }
}

@Serializable
data object My : MainTabRoute

@Serializable
data object MyJogbo : Route