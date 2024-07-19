package com.hankki.feature.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.home.navigation.homeNavGraph
import com.hankki.feature.login.navigation.loginNavGraph
import com.hankki.feature.login.navigation.onboardingNavGraph
import com.hankki.feature.main.splash.navigation.splashNavGraph
import com.hankki.feature.my.navigation.myNavGraph
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.navigation.reportNavGraph
import com.hankki.feature.storedetail.navigation.storeDetailNavGraph
import com.hankki.feature.universityselection.navigation.UniversitySelection
import com.hankki.feature.universityselection.navigation.universitySelectionNavGraph
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val localContextResource = LocalContext.current.resources
    val onShowSnackBar: (Int) -> Unit = { errorMessage ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(localContextResource.getString(errorMessage))
        }
    }

    Scaffold(
        content = { paddingValue ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { ExitTransition.None }
                ) {
                    splashNavGraph(
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo(navigator.navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(
                                navOptions = navOptions,
                                isNewUniversity = true
                            )
                        },
                        navigateToLogIn = navigator::navigateToLogin
                    )
                    homeNavGraph(
                        paddingValues = paddingValue,
                        onShowSnackBar = {},
                        navigateStoreDetail = navigator::navigateToStoreDetail,
                        navigateToUniversitySelection = navigator::navigateToUniversity
                    )
                    reportNavGraph(
                        navigateReport = { latitude, longitude, location, address ->
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToReport(
                                LocationModel(
                                    latitude,
                                    longitude,
                                    location,
                                    address
                                ), navOptions
                            )
                        },
                        navigateToSearchStore = {
                            navigator.navigateToSearchStore()
                        },
                        navigateUp = navigator::navigateUpIfNotHome,
                        navigateToReportFinish = { count, storeName, storeId ->
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToReportFinish(
                                count,
                                storeName,
                                storeId,
                                navOptions
                            )
                        },
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(navOptions)
                        },
                        navigateToStoreDetail = { storeId ->
                            val navOptions = navOptions {
                                popUpTo(navigator.navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                            navigator.navigateToStoreDetail(storeId, navOptions)
                        }
                    )
                    myNavGraph(
                        paddingValues = paddingValue,
                        navigateUp = navigator::navigateUpIfNotHome,
                        navigateToMyJogbo = navigator::navigateToMyJogbo,
                        navigateToMyStore = navigator::navigateToMyStore,
                        navigateToJogboDetail = navigator::navigateToMyJogboDetail,
                        navigateToNewJogbo = navigator::navigateToNewJogbo,
                        navigateToStoreDetail = navigator::navigateToStoreDetail
                    )
                    loginNavGraph(
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(navOptions)
                        },
                        navigateToOnboarding = navigator::navigateToOnboarding
                    )
                    onboardingNavGraph(
                        navigateToUniversity = {
                            navigator.navigateToUniversity()
                        }
                    )
                    universitySelectionNavGraph(
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(navOptions, true)
                        }
                    )
                    storeDetailNavGraph(navigateUp = navigator::navigateUpIfNotHome)
                }
            }
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    )
}

@Composable
private fun MainBottomBar(
    visible: Boolean,
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Column(
            modifier = Modifier.background(White)
        ) {
            HorizontalDivider(
                color = Gray100
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .height(75.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                tabs.forEach { tab ->
                    MainBottomBarItem(
                        tab = tab,
                        selected = (tab == currentTab),
                        onClick = { onTabSelected(tab) },
                    )
                }
            }
        }
    }
}


@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(
                if (selected) {
                    tab.selectedIconResource
                } else {
                    tab.unselectedIconResource
                }
            ),
            contentDescription = tab.contentDescription,
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    HankkijogboTheme {
        MainScreen()
    }
}
