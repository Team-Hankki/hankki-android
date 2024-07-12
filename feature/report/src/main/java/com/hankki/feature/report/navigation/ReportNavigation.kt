package com.hankki.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.report.main.ReportRoute
import com.hankki.feature.report.searchstore.SearchStoreRoute
import kotlinx.serialization.Serializable

fun NavController.navigateReport() {
    navigate(Report)
}

fun NavController.navigateSearchStore() {
    navigate(SearchStore)
}

fun NavGraphBuilder.reportNavGraph(
    navigateUp: () -> Unit,
    navigateSearchStore: () -> Unit,
) {
    composable<Report> {
        ReportRoute(
            navigateUp = navigateUp,
            navigateSearchStore = navigateSearchStore
        )
    }
    composable<SearchStore> {
        SearchStoreRoute(navigateUp)
    }
}

@Serializable
data object Report : MainTabRoute

@Serializable
data object SearchStore : MainTabRoute
