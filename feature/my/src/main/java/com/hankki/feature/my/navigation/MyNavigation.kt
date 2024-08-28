package com.hankki.feature.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.my.myjogbo.MyJogboRoute
import com.hankki.feature.my.myjogbodetail.MyJogboDetailRoute
import com.hankki.feature.my.mypage.MyRoute
import com.hankki.feature.my.mystore.MyStoreRoute
import com.hankki.feature.my.newjogbo.NewJogboRoute
import kotlinx.serialization.Serializable

fun NavController.navigateMy(navOptions: NavOptions) {
    navigate(My, navOptions)
}

fun NavController.navigateMyJogbo() {
    navigate(MyJogbo)
}

fun NavController.navigateMyStore(type: String) {
    navigate(MyStore(type))
}

fun NavController.navigateMyJogboDetail(favoriteId: Long,navOptions: NavOptions) {
    navigate(MyJogboDetail(favoriteId = favoriteId),navOptions)
}

fun NavController.navigateNewJogbo() {
    navigate(NewJogbo)
}

fun NavGraphBuilder.myNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMyJogbo: () -> Unit,
    navigateToMyStore: (String) -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    navigateToStoreDetail: (Long) -> Unit,
    navigateToHome: () -> Unit
) {
    composable<My> {
        MyRoute(paddingValues, navigateToMyJogbo, navigateToMyStore)
    }
    composable<MyJogbo> {
        MyJogboRoute(navigateUp, navigateToJogboDetail, navigateToNewJogbo)
    }
    composable<MyStore> { backStackEntry ->
        val type = backStackEntry.toRoute<MyStore>()
        MyStoreRoute(
            type = type.type,
            navigateUp = navigateUp,
            navigateToDetail = navigateToStoreDetail
        )
    }
    composable<MyJogboDetail> { backStackEntry ->
        val jogbo = backStackEntry.toRoute<MyJogboDetail>()
        MyJogboDetailRoute(
            favoriteId = jogbo.favoriteId,
            navigateToDetail = navigateToStoreDetail,
            navigateUp = navigateUp,
            navigateToHome = navigateToHome
        )
    }
    composable<NewJogbo> {
        NewJogboRoute(navigateUp)
    }
}

@Serializable
data object My : MainTabRoute

@Serializable
data object MyJogbo : Route

@Serializable
data class MyStore(
    val type: String,
) : Route

@Serializable
data class MyJogboDetail(
    val favoriteId: Long
) : Route

@Serializable
data object NewJogbo : Route
