package com.hankki.feature.dummy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.eoyeongbooyeong.core.navigation.Route
import com.hankki.feature.dummy.DummyRoute
import kotlinx.serialization.Serializable

fun NavController.navigateDummy(navOptions: NavOptions) {
    navigate(Dummy, navOptions)
}

fun NavGraphBuilder.dummyNavGraph(
    onSignInClick: () -> Unit,
    onShowErrorSnackBar: (Int) -> Unit,
) {
    composable<Dummy> {
        DummyRoute(
            onSignInClick = onSignInClick,
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}

@Serializable
data object Dummy: Route
