package com.hankki.feature.main

import androidx.compose.runtime.Composable
import com.hankki.core.designsystem.R
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.my.navigation.My
import com.hankki.feature.report.navigation.Report

internal enum class MainTab(
    val selectedIconResource: Int,
    val unselectedIconResource: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
) {
    HOME(
        selectedIconResource = R.drawable.ic_home_selected,
        unselectedIconResource = R.drawable.ic_home_unselected,
        contentDescription = "홈",
        Home
    ),
    REPORT(
        selectedIconResource = R.drawable.ic_jaebo,
        unselectedIconResource = R.drawable.ic_jaebo,
        contentDescription = "제보하기",
        Report,
    ),
    MY(
        selectedIconResource = R.drawable.ic_my_selected,
        unselectedIconResource = R.drawable.ic_my_unselected,
        contentDescription = "마이페이지",
        My,
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
