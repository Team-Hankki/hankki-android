package com.hankki.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.hankki.core.navigation.Route
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.home.navigation.navigateHome
import com.hankki.feature.login.navigation.Login
import com.hankki.feature.login.navigation.navigateOnboarding
import com.hankki.feature.main.splash.navigation.Splash
import com.hankki.feature.my.navigation.MyJogbo
import com.hankki.feature.my.navigation.MyJogboDetail
import com.hankki.feature.my.navigation.navigateMy
import com.hankki.feature.my.navigation.navigateMyJogbo
import com.hankki.feature.my.navigation.navigateMyJogboDetail
import com.hankki.feature.my.navigation.navigateMyStore
import com.hankki.feature.my.navigation.navigateNewJogbo
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.navigation.navigateToReport
import com.hankki.feature.report.navigation.navigateToReportFinish
import com.hankki.feature.report.navigation.navigateToSearchStore
import com.hankki.feature.storedetail.navigation.navigateAddMenu
import com.hankki.feature.storedetail.navigation.navigateDeleteSuccess
import com.hankki.feature.storedetail.navigation.navigateEditMenu
import com.hankki.feature.storedetail.navigation.navigateEditMod
import com.hankki.feature.storedetail.navigation.navigateEditModSuccess
import com.hankki.feature.storedetail.navigation.navigateStoreDetail
import com.hankki.feature.storedetail.navigation.navigateToStoreDetailReport
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
            MainTab.Home -> navController.navigateHome(
                navOptions = navOptions {
                    popUpTo<Home> {
                        inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            )

            MainTab.Report -> navController.navigateToReport()
            MainTab.Mypage -> navController.navigateMy(
                navOptions {
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

    fun navigateToLogin(navOptions: NavOptions? = null) {
        val options = navOptions ?: navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
            }
            launchSingleTop = true
        }
        navController.navigate(Login, options)
    }

    fun navigateToOnboarding(navOptions: NavOptions) {
        navController.navigateOnboarding(navOptions)
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
    ) {
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

    fun navigateToSearchStore(navOptions: NavOptions? = null) {
        navController.navigateToSearchStore(navOptions)
    }

    fun navigateToUniversity(navOptions: NavOptions? = null) {
        navController.navigateToUniversitySelection(navOptions)
    }

    fun navigateToMy() {
        navController.navigateMy(navOptions {
            popUpTo<MyJogbo> {
                inclusive = true
            }
            launchSingleTop = true
        })
    }

    fun navigateToMyJogbo(isDeletedDialogNeed: Boolean) {
        navController.navigateMyJogbo(isDeletedDialogNeed, navOptions {
            popUpTo<MyJogboDetail> {
                inclusive = true
            }
            launchSingleTop = true
        })
    }

    fun navigateToMyStore(type: String) {
        navController.navigateMyStore(type)
    }

    fun navigateToMyJogboDetail(favoriteId: Long) {
        navController.navigateMyJogboDetail(favoriteId = favoriteId, navOptions = navOptions {
            popUpTo<MyJogboDetail> {
                saveState = true
            }
            restoreState = true
        })
    }

    fun navigateToNewJogbo() {
        navController.navigateNewJogbo()
    }

    fun navigateToNewSharedJogbo(isSharedJogbo: Boolean, favoriteId: Long) {
        navController.navigateNewJogbo(isSharedJogbo, favoriteId)
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    } && (currentTab?.showBottomSheet ?: true)

    fun navigateToEditMod(storeId: Long, menuId: Long, menuName: String, price: String) {
        navController.navigateEditMod(
            storeId = storeId,
            menuId = menuId,
            menuName = menuName,
            price = price,
            navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        )
    }

    fun navigateToEditSuccess(storeId: Long) {
        navController.navigateEditModSuccess(
            storeId = storeId,
            navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        )
    }

    fun navigateToAddMenu(storeId: Long, navOptions: NavOptions? = null) {
        val options = navOptions ?: navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
            }
            launchSingleTop = true
        }
        navController.navigateAddMenu(storeId, options)
    }

    fun navigateToEditMenu(storeId: Long, navOptions: NavOptions? = null) {
        val options = navOptions ?: navOptions {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
            }
            launchSingleTop = true
        }
        navController.navigateEditMenu(storeId, options)
    }

    fun navigateToDeleteSuccess(storeId: Long) {
        navController.navigateDeleteSuccess(
            storeId = storeId,
            navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        )
    }

    fun navigateToStoreDetailReport(storeId: Long) {
        navController.navigateToStoreDetailReport(storeId)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
