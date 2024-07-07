package com.hankki.feature.universityselection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.universityselection.UniversitySelectionRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUniversitySelection() {
    navigate(UniversitySelection)
}

fun NavGraphBuilder.universitySelectionNavGraph(
    navigateUpIfNotHome: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable<UniversitySelection> {
        UniversitySelectionRoute(
            navigateUpIfNotHome = navigateUpIfNotHome,
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object UniversitySelection : Route