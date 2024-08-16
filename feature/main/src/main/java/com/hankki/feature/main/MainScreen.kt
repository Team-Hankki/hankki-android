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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.snackbar.HankkiTextSnackBar
import com.hankki.core.designsystem.component.snackbar.HankkiTextSnackBarWithButton
import com.hankki.core.designsystem.component.snackbar.HankkiWhiteSnackBarWithButton
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.home.navigation.homeNavGraph
import com.hankki.feature.login.navigation.Login
import com.hankki.feature.login.navigation.Onboarding
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SNACK_BAR_DURATION = 2000L

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    val errorSnackBarHostState = remember { SnackbarHostState() }
    val onShowErrorSnackBar: (String?) -> Unit = { text ->
        coroutineScope.launch {
            errorSnackBarHostState.currentSnackbarData?.dismiss()

            val job = launch {
                errorSnackBarHostState.showSnackbar(
                    message = text ?: "오류가 발생했어요. 다시 시도해주세요.",
                )
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
        }
    }

    val textSnackBarWithButtonHostState = remember { SnackbarHostState() }
    val onShowTextSnackBarWithButton: (String, Long) -> Unit = { message, jogboId ->
        coroutineScope.launch {
            textSnackBarWithButtonHostState.currentSnackbarData?.dismiss()

            val job = launch {
                textSnackBarWithButtonHostState.showSnackbar(
                    message = "$message/$jogboId",
                )
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
        }
    }

    val whiteSnackBarWithButtonHostState = remember { SnackbarHostState() }
    val onShowWhiteSnackBarWithButton: (String, Long) -> Unit = { message, jogboId ->
        coroutineScope.launch {
            whiteSnackBarWithButtonHostState.currentSnackbarData?.dismiss()

            val job = launch {
                whiteSnackBarWithButtonHostState.showSnackbar(
                    message = "$message/$jogboId",
                )
            }
            delay(SNACK_BAR_DURATION)
            job.cancel()
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
                            navigator.navigateToHome(navOptions = navOptions)
                        },
                        navigateToLogIn = {
                            val navOptions = navOptions {
                                popUpTo(navigator.navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToLogin(
                                navOptions = navOptions
                            )
                        },
                    )
                    homeNavGraph(
                        paddingValues = paddingValue,
                        onShowSnackBar = onShowTextSnackBarWithButton,
                        onShowTextSnackBar = onShowErrorSnackBar,
                        navigateStoreDetail = navigator::navigateToStoreDetail,
                        navigateToUniversitySelection = navigator::navigateToUniversity,
                        navigateToAddNewJogbo = navigator::navigateToNewJogbo
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
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToStoreDetail(storeId, navOptions)
                        },
                        navigateToAddNewJogbo = navigator::navigateToNewJogbo,
                        onShowSnackBar = onShowWhiteSnackBarWithButton
                    )
                    myNavGraph(
                        paddingValues = paddingValue,
                        navigateUp = navigator::navigateUpIfNotHome,
                        navigateToMyJogbo = navigator::navigateToMyJogbo,
                        navigateToMyStore = navigator::navigateToMyStore,
                        navigateToJogboDetail = navigator::navigateToMyJogboDetail,
                        navigateToNewJogbo = navigator::navigateToNewJogbo,
                        navigateToStoreDetail = navigator::navigateToStoreDetail,
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo<Home> {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(navOptions)
                        }
                    )
                    loginNavGraph(
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo(navigator.navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(navOptions = navOptions)
                        },
                        navigateToOnboarding = {
                            val navOptions = navOptions {
                                popUpTo<Login> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToOnboarding(navOptions)
                        }
                    )

                    onboardingNavGraph(
                        navigateToUniversity = {
                            val navOptions = navOptions {
                                popUpTo<Onboarding> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToUniversity(navOptions)
                        }
                    )
                    universitySelectionNavGraph(
                        navigateToHome = {
                            val navOptions = navOptions {
                                popUpTo<UniversitySelection> {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                            navigator.navigateToHome(
                                navOptions = navOptions
                            )
                        }
                    )
                    storeDetailNavGraph(
                        navigateUp = navigator::navigateUpIfNotHome,
                        navigateToAddNewJogbo = navigator::navigateToNewJogbo,
                        onShowSnackBar = onShowTextSnackBarWithButton
                    )
                }

                if (!isConnected) {
                    SingleButtonDialog(
                        title = "네트워크 오류가 발생했어요",
                        description = "네트워크 연결 상태를 확인하고 다시 시도해주세요",
                        buttonTitle = "확인"
                    ) {
                        navigator.navigateUpIfNotHome()
                    }
                }

                SnackbarHost(
                    hostState = whiteSnackBarWithButtonHostState,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 35.dp)
                ) { snackBarData ->
                    runCatching {
                        val (message, jogboId) = snackBarData.visuals.message.split("/")

                        HankkiWhiteSnackBarWithButton(
                            message = message
                        ) {
                            snackBarData.dismiss()

                            navigator.navigateToMyJogboDetail(
                                jogboId.toLong()
                            )
                        }
                    }.onFailure {
                        HankkiWhiteSnackBarWithButton(
                            message = "오류가 발생했어요",
                            buttonText = "닫기"
                        ) {
                            snackBarData.dismiss()
                        }
                    }
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
        snackbarHost = {
            SnackbarHost(hostState = errorSnackBarHostState) { snackBarData ->
                HankkiTextSnackBar(message = snackBarData.visuals.message)
            }

            SnackbarHost(hostState = textSnackBarWithButtonHostState) { snackBarData ->
                runCatching {
                    val (message, jogboId) = snackBarData.visuals.message.split("/")

                    HankkiTextSnackBarWithButton(
                        message = message,
                    ) {
                        snackBarData.dismiss()

                        navigator.navigateToMyJogboDetail(
                            jogboId.toLong()
                        )
                    }
                }.onFailure {
                    HankkiTextSnackBarWithButton(
                        message = snackBarData.visuals.message,
                        buttonText = "닫기"
                    ) {
                        snackBarData.dismiss()
                    }
                }
            }
        },
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
            modifier = Modifier.shadow(24.dp).background(White)
        ) {
            HorizontalDivider(
                color = Gray100
            )
            Spacer(modifier = Modifier.height(11.dp))
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .height(47.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
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
