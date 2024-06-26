package com.hankki.feature.main

import androidx.compose.runtime.Composable
import com.eoyeongbooyeong.core.navigation.Route
import kotlinx.serialization.Serializable

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
) {
    HOME(
        iconResId = R.drawable.ic_launcher_background,
        contentDescription = "홈",
        MainTabRoute.Home
    ),
    REPORT(
        iconResId = R.drawable.ic_launcher_background,
        contentDescription = "제보하기",
        MainTabRoute.Report,
    ),
    MY(
        iconResId = R.drawable.ic_launcher_background,
        contentDescription = "마이페이지",
        MainTabRoute.My,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Report : MainTabRoute

    @Serializable
    data object My : MainTabRoute
}
