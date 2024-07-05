package com.hankki.feature.university.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.university.UniversityRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUniversity() {
    navigate(University)
}

fun NavGraphBuilder.universityNavgraph(navigateToHome: () -> Unit) {
    composable<University> {
        UniversityRoute(navigateToHome = navigateToHome)
    }
}

@Serializable
data object University : Route