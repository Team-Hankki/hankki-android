package com.hankki.feature.dummy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.dummy.DummyRoute
import kotlinx.serialization.Serializable

fun NavController.navigateDummy() {
    navigate(Dummy)
}

fun NavGraphBuilder.dummyNavGraph(onShowErrorSnackBar: (String) -> Unit) {
    composable<Dummy> {
        DummyRoute(
            onShowErrorSnackBar = onShowErrorSnackBar
        )
    }
}

@Serializable
data object Dummy : Route
