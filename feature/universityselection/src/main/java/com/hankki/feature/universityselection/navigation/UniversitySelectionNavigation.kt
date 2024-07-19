package com.hankki.feature.universityselection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hankki.core.navigation.Route
import com.hankki.feature.universityselection.UniversitySelectionRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToUniversitySelection(navOptions: NavOptions?) {
    navigate(UniversitySelection, navOptions)
}

fun NavGraphBuilder.universitySelectionNavGraph(
    navigateToHome: () -> Unit
) {
    composable<UniversitySelection> {
        UniversitySelectionRoute(
            navigateToHome = navigateToHome
        )
    }
}

@Serializable
data object UniversitySelection : Route
