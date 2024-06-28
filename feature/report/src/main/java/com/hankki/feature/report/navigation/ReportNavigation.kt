package com.hankki.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.report.ReportRoute
import kotlinx.serialization.Serializable

fun NavController.navigateReport(navOptions: NavOptions) {
    navigate(Report, navOptions)
}

fun NavGraphBuilder.reportNavGraph() {
    composable<Report> {
        ReportRoute()
    }
}

@Serializable
data object Report : MainTabRoute
