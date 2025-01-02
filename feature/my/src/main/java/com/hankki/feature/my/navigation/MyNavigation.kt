package com.hankki.feature.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.hankki.core.common.BuildConfig.KAKAO_NATIVE_APP_KEY
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

fun NavController.navigateMyJogbo(isDeletedDialogNeed: Boolean = false) {
    navigate(MyJogbo(isDeletedDialogNeed))
}

fun NavController.navigateMyStore(type: String) {
    navigate(MyStore(type))
}

fun NavController.navigateMyJogboDetail(favoriteId: Long, navOptions: NavOptions) {
    navigate(MyJogboDetail(favoriteId = favoriteId), navOptions)
}

fun NavController.navigateNewJogbo(isSharedJogbo: Boolean = false, favoriteId: Long = 0L) {
    navigate(NewJogbo(isSharedJogbo, favoriteId))
}

fun NavGraphBuilder.myNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMyJogbo: (Boolean) -> Unit,
    navigateToMyStore: (String) -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    navigateToNewSharedJogbo: (Boolean, Long) -> Unit,
    navigateToStoreDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    isSharedJogbo: Boolean
) {
    composable<My> {
        MyRoute(paddingValues, navigateToMyJogbo, navigateToMyStore)
    }
    composable<MyJogbo> { backStackEntry ->
        val isDeletedDialogNeed = backStackEntry.toRoute<MyJogbo>()

        MyJogboRoute(
            isDeletedDialogNeed.isDeletedDialogNeed,
            navigateUp,
            navigateToJogboDetail,
            navigateToNewJogbo
        )
    }
    composable<MyStore> { backStackEntry ->
        val type = backStackEntry.toRoute<MyStore>()
        MyStoreRoute(
            type = type.type,
            navigateUp = navigateUp,
            navigateToDetail = navigateToStoreDetail
        )
    }
    composable<MyJogboDetail>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "kakao${KAKAO_NATIVE_APP_KEY}://kakaolink?favoriteId={favoriteId}"
            }
        )
    ) { backStackEntry ->
        val jogbo = backStackEntry.toRoute<MyJogboDetail>()

        MyJogboDetailRoute(
            favoriteId = jogbo.favoriteId,
            navigateToDetail = navigateToStoreDetail,
            navigateUp = navigateUp,
            navigateToHome = navigateToHome,
            navigateToNewSharedJogbo = navigateToNewSharedJogbo,
            navigateToMyJogbo = navigateToMyJogbo,
            isSharedJogbo = isSharedJogbo
        )
    }
    composable<NewJogbo> { backStackEntry ->
        val jogbo = backStackEntry.toRoute<NewJogbo>()
        NewJogboRoute(
            navigateUp = navigateUp,
            navigateToMyJogbo = navigateToMyJogbo,
            isSharedJogbo = jogbo.isSharedJogbo,
            favoriteId = jogbo.favoriteId
        )
    }
}

@Serializable
data object My : MainTabRoute

@Serializable
data class MyJogbo(
    val isDeletedDialogNeed: Boolean
) : Route

@Serializable
data class MyStore(
    val type: String,
) : Route

@Serializable
data class MyJogboDetail(
    val favoriteId: Long
) : Route

@Serializable
data class NewJogbo(
    val isSharedJogbo: Boolean,
    val favoriteId: Long
) : Route
