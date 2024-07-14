package com.hankki.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.report.main.ReportRoute
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.searchstore.SearchStoreRoute
import kotlinx.serialization.Serializable

fun NavController.navigateReport(
    latitude: Float = 0f,
    longitude: Float = 0f,
    location: String = "",
    address: String = "",
    navOptions: NavOptions? = null
) {
    navigate(Report(latitude, longitude, location, address), navOptions)
}

fun NavController.navigateSearchStore() {
    navigate(SearchStore)
}

fun NavGraphBuilder.reportNavGraph(
    navigateReport: (
        latitude: Float,
        longitude: Float,
        location: String,
        address: String,
    ) -> Unit,
    navigateSearchStore: () -> Unit,
    navigateUp: () -> Unit,
) {
    composable<Report> { backStackEntry ->
        val items = backStackEntry.toRoute<Report>()
        ReportRoute(
            location = LocationModel(
                items.latitude,
                items.longitude,
                items.location,
                items.address
            ),
            navigateSearchStore = navigateSearchStore,
            navigateUp = navigateUp
        )
    }
    composable<SearchStore> {
        SearchStoreRoute(
            navigateReport = { latitude, longitude, location, address ->
                navigateReport(latitude, longitude, location, address)
            },
            navigateUp = navigateUp
        )
    }
}

@Serializable
data class Report(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val location: String = "",
    val address: String = "",
) : MainTabRoute

@Serializable
data object SearchStore : MainTabRoute
