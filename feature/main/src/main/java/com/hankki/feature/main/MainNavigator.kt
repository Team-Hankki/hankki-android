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
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.home.navigation.navigateHome
import com.hankki.feature.login.navigation.Login
import com.hankki.feature.login.navigation.navigateLogin
import com.hankki.feature.main.splash.navigation.Splash
import com.hankki.feature.login.navigation.navigateOnboarding
import com.hankki.feature.my.navigation.navigateMy
import com.hankki.feature.my.navigation.navigateMyJogbo
import com.hankki.feature.my.navigation.navigateMyJogboDetail
import com.hankki.feature.my.navigation.navigateMyStore
import com.hankki.feature.my.navigation.navigateNewJogbo
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

    val startDestination = Splash

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions =
            navOptions{
                popUpTo<Home> {
                    inclusive = false
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            })
            MainTab.REPORT -> navController.navigateToReport()
            MainTab.MY -> navController.navigateMy(
                navOptions{
                    popUpTo<Home> {
                        inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            )
        }
    }

    fun navigateToLogin() {
        navController.navigateLogin()
    }

    fun navigateToOnboarding() {
        navController.navigateOnboarding()
    }

    private fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToStoreDetail(storeId: Long, navOptions: NavOptions? = null) {
        navController.navigateStoreDetail(storeId, navOptions)
    }

    fun navigateUpIfNotHome() {
        if (!isSameCurrentDestination<Home>()) {
            navigateUp()
        }
    }

    fun navigateToHome(
        navOptions: NavOptions,
        isNewUniversity: Boolean = false,
    ) {
        navController.navigate(Home(isNewUniversity), navOptions)
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

    fun navigateToSearchStore(navOptions: NavOptions? = null) {
        navController.navigateToSearchStore(navOptions)
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

    fun navigateToMyJogboDetail(favoriteId:Long) {
        navController.navigateMyJogboDetail(favoriteId = favoriteId)
    }

    fun navigateToNewJogbo() {
        navController.navigateNewJogbo()
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
