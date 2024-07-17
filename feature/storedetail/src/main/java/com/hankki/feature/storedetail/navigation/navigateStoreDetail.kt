package com.hankki.feature.storedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.storedetail.StoreDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateStoreDetail(storeId: Long) {
    navigate("storeDetail/$storeId")
}

fun NavGraphBuilder.storeDetailNavGraph(
    navigateUp: () -> Unit
) {
    composable("storeDetail/{storeId}") { backStackEntry ->
        val storeId = backStackEntry.arguments?.getString("storeId")?.toLongOrNull() ?: return@composable
        StoreDetailRoute(storeId)
    }
}

@Serializable
data object StoreDetail : MainTabRoute
