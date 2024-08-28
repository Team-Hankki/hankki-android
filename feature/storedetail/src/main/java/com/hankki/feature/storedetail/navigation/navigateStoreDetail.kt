package com.hankki.feature.storedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.storedetail.StoreDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateStoreDetail(storeId: Long, navOptions: NavOptions?) {
    navigate(StoreDetail(storeId = storeId), navOptions)
}

fun NavGraphBuilder.storeDetailNavGraph(
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    onShowTextSnackBar: (String) -> Unit,
    ) {
    composable<StoreDetail> { backStackEntry ->
        val items = backStackEntry.toRoute<StoreDetail>()
        StoreDetailRoute(
            storeId = items.storeId,
            navigateUp = navigateUp,
            navigateToAddNewJogbo = navigateToAddNewJogbo,
            onShowSnackBar = onShowSnackBar,
            onShowTextSnackBar = onShowTextSnackBar
        )
    }
}

@Serializable
data class StoreDetail(
    val storeId: Long,
) : MainTabRoute
