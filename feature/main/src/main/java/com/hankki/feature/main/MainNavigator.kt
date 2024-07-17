package com.hankki.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.hankki.core.navigation.Route
import com.hankki.feature.dummy.navigation.navigateDummy
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.home.navigation.navigateHome
import com.hankki.feature.login.navigation.Login
import com.hankki.feature.login.navigation.navigateLogin
import com.hankki.feature.my.navigation.navigateMy
import com.hankki.feature.my.navigation.navigateMyJogbo
import com.hankki.feature.my.navigation.navigateMyJogboDetail
import com.hankki.feature.my.navigation.navigateMyStore
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.navigation.navigateToReport
import com.hankki.feature.report.navigation.navigateToReportFinish
import com.hankki.feature.report.navigation.navigateToSearchStore
import com.hankki.feature.storedetail.navigation.navigateStoreDetail
import com.hankki.feature.universityselection.navigation.navigateToUniversitySelection

internal class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Home

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.REPORT -> navController.navigateToReport()
            MainTab.MY -> navController.navigateMy(navOptions)
        }
    }

    fun navigateToDummy() {
        navController.navigateDummy()
    }

    fun navigateToLogin() {
        navController.navigateLogin()
    }

    private fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToStoreDetail() {
        navController.navigateStoreDetail(3L)
    }

    fun navigateUpIfNotHome() {
        if (!isSameCurrentDestination<Home>()) {
            navigateUp()
        }
    }

    fun navigateToHome(navOptions: NavOptions) {
        navController.navigate(Home, navOptions)
    }

    fun navigateToReport(location: LocationModel, navOptions: NavOptions) {
        navController.navigateToReport(
            latitude = location.latitude,
            longitude = location.longitude,
            location = location.location,
            address = location.address,
            navOptions = navOptions
        )
    }

    fun navigateToReportFinish(
        count: Long,
        storeName: String,
        storeId: Long,
        navOptions: NavOptions,
    ) {
        navController.navigateToReportFinish(
            count = count,
            storeName = storeName,
            storeId = storeId,
            navOptions = navOptions
        )
    }

    fun navigateToSearchStore() {
        navController.navigateToSearchStore()
    }

    fun navigateToUniversity() {
        navController.navigateToUniversitySelection()
    }

    fun navigateToMyJogbo() {
        navController.navigateMyJogbo()
    }

    fun navigateToMyStore(type: String) {
        navController.navigateMyStore(type)
    }

    fun navigateToMyJogboDetail() {
        navController.navigateMyJogboDetail()
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    } && (currentTab?.showBottomSheet ?: true)
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
