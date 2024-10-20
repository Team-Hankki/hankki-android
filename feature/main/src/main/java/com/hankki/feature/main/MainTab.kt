package com.hankki.feature.main

import androidx.compose.runtime.Composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.my.navigation.My
import com.hankki.feature.report.navigation.Report

internal enum class MainTab(
    val selectedIconResource: Int,
    val unselectedIconResource: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
    val showBottomSheet: Boolean = true,
) {
    Home(
        selectedIconResource = R.drawable.ic_home_selected,
        unselectedIconResource = R.drawable.ic_home_unselected,
        contentDescription = "홈",
        route = com.hankki.feature.home.navigation.Home
    ),
    Report(
        selectedIconResource = R.drawable.ic_jaebo_selected,
        unselectedIconResource = R.drawable.ic_jaebo_unselected,
        contentDescription = "제보하기",
        route = Report(),
        false
    ),
    Mypage(
        selectedIconResource = R.drawable.ic_profile_selected,
        unselectedIconResource = R.drawable.ic_profile_unselected,
        contentDescription = "마이",
        route = My
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
