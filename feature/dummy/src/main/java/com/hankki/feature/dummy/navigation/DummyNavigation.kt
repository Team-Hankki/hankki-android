package com.hankki.feature.dummy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.dummy.DummyRoute
import kotlinx.serialization.Serializable

fun NavController.navigateDummy(navOptions: NavOptions) {
    navigate(Dummy, navOptions)
}

fun NavGraphBuilder.dummyNavGraph() {
    composable<Dummy> {
        DummyRoute()
    }
}

@Serializable
data object Dummy: Route
