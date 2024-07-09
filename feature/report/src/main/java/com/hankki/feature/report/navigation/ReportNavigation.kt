package com.hankki.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.report.ReportRoute
import kotlinx.serialization.Serializable

fun NavController.navigateReport() {
    navigate(Report)
}

fun NavGraphBuilder.reportNavGraph(
    navigateToLogin: () -> Unit,
    navigateToUniversity: () -> Unit,
) {
    composable<Report> {
        ReportRoute(navigateToLogin, navigateToUniversity)
    }
}

@Serializable
data object Report : MainTabRoute
