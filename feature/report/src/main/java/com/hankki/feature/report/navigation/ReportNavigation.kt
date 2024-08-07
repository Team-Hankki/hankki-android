package com.hankki.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.report.finish.ReportFinishRoute
import com.hankki.feature.report.main.ReportRoute
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.searchstore.SearchStoreRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToReport(
    latitude: Float = 0f,
    longitude: Float = 0f,
    location: String = "",
    address: String = "",
    navOptions: NavOptions? = null,
) {
    navigate(
        Report(
            latitude = latitude,
            longitude = longitude,
            location = location,
            address = address
        ),
        navOptions
    )
}

fun NavController.navigateToSearchStore(navOptions: NavOptions?,) {
    navigate(SearchStore, navOptions)
}

fun NavController.navigateToReportFinish(
    count: Long,
    storeName: String,
    storeId: Long,
    navOptions: NavOptions? = null,
) {
    navigate(
        ReportFinish(
            count = count,
            storeName = storeName,
            storeId = storeId
        ),
        navOptions
    )
}

fun NavGraphBuilder.reportNavGraph(
    navigateReport: (
        latitude: Float,
        longitude: Float,
        location: String,
        address: String,
    ) -> Unit,
    navigateToSearchStore: () -> Unit,
    navigateUp: () -> Unit,
    navigateToReportFinish: (
        count: Long,
        storeName: String,
        storeId: Long,
    ) -> Unit,
    navigateToStoreDetail: (storeId: Long) -> Unit,
    navigateToHome: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    onShowSnackBar: (String) -> Unit,
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
            navigateSearchStore = navigateToSearchStore,
            navigateUp = navigateUp,
            navigateToReportFinish = navigateToReportFinish,
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
    composable<ReportFinish> { backStackEntry ->
        val items = backStackEntry.toRoute<ReportFinish>()
        ReportFinishRoute(
            count = items.count,
            storeName = items.storeName,
            storeId = items.storeId,
            navigateToHome = navigateToHome,
            navigateToStoreDetail = navigateToStoreDetail,
            navigateToAddNewJogbo = navigateToAddNewJogbo,
            onShowSnackBar = onShowSnackBar
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

@Serializable
data class ReportFinish(
    val storeName: String,
    val storeId: Long,
    val count: Long,
) : MainTabRoute
