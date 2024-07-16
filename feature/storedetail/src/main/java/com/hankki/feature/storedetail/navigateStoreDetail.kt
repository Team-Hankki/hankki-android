package com.hankki.feature.storedetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateStoreDetail() {
    navigate(StoreDetail)
}

fun NavGraphBuilder.storeDetailNavGraph(
    navigateUp: () -> Unit
) {
    composable<StoreDetail> {
        StoreDetailRoute()
    }
}

@Serializable
data object StoreDetail : MainTabRoute