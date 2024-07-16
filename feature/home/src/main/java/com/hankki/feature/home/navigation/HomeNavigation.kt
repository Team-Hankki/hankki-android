package com.hankki.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(Home, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    paddingValues: PaddingValues,
    onShowSnackBar: (Int) -> Unit,
    navigateStoreDetail: () -> Unit,
    navigateToUniversitySelection: () -> Unit
) {
    composable<Home> {
        HomeRoute(
            paddingValues = paddingValues,
            onShowSnackBar = onShowSnackBar,
            navigateStoreDetail = navigateStoreDetail,
            navigateToUniversitySelection = navigateToUniversitySelection
        )
    }
}

@Serializable
data object Home : MainTabRoute
