package com.hankki.feature.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.my.mypage.MyRoute
import com.hankki.feature.my.myjogbo.MyJogboRoute
import com.hankki.feature.my.myjogbodetail.MyJogboDetailRoute
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

fun NavController.navigateMyJogboDetail(favoriteId : Long) {
    navigate(MyJogboDetail(favoriteId = favoriteId))
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
    navigateToNewJogbo: () -> Unit
) {
    composable<My> {
        MyRoute(paddingValues, navigateToMyJogbo, navigateToMyStore)
    }
    composable<MyJogbo> {
        MyJogboRoute(paddingValues, navigateUp, navigateToJogboDetail, navigateToNewJogbo)
    }
    composable<MyStore> { backStackEntry ->
        val type = backStackEntry.toRoute<MyStore>()
        MyStoreRoute(paddingValues, navigateUp, type.type)
    }
    composable<MyJogboDetail> {backStackEntry ->
        val jogbo = backStackEntry.toRoute<MyJogboDetail>()
        MyJogboDetailRoute(jogbo.favoriteId ,paddingValues, navigateUp)
    }
    composable<NewJogbo> {
        NewJogboRoute(paddingValues,navigateUp)
    }
}

@Serializable
data object My : MainTabRoute

@Serializable
data object MyJogbo : Route

@Serializable
data class MyStore(
    val type: String
) : Route

@Serializable
data class MyJogboDetail(
    val favoriteId: Long
) : Route

@Serializable
data object NewJogbo : Route
