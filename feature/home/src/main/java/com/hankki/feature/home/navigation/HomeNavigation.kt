package com.hankki.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateHome(
    isNewUniversity: Boolean = false,
    navOptions: NavOptions,
) {
    navigate(Home(isNewUniversity = isNewUniversity), navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    paddingValues: PaddingValues,
    onShowSnackBar: (Int) -> Unit,
    navigateStoreDetail: (Long) -> Unit,
    navigateToUniversitySelection: () -> Unit,
) {
    composable<Home> { backStackEntry ->
        val items = backStackEntry.toRoute<Home>()
        HomeRoute(
            paddingValues = paddingValues,
            onShowSnackBar = onShowSnackBar,
            navigateStoreDetail = navigateStoreDetail,
            isNewUniversity = items.isNewUniversity,
            navigateToUniversitySelection = navigateToUniversitySelection
        )
    }
}

@Serializable
data class Home(
    val isNewUniversity: Boolean = false
) : MainTabRoute
