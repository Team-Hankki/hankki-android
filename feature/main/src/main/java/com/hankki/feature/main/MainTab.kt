package com.hankki.feature.main

import androidx.compose.runtime.Composable
import com.hankki.core.navigation.MainTabRoute
import com.hankki.core.navigation.Route
import com.hankki.feature.home.navigation.Home
import com.hankki.feature.my.navigation.My
import com.hankki.feature.report.navigation.Report

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: MainTabRoute,
) {
    HOME(
        iconResId = R.drawable.ic_launcher_background,
        contentDescription = "홈",
        Home
    ),
    REPORT(
        iconResId = R.drawable.ic_launcher_background,
        contentDescription = "제보하기",
        Report,
    ),
    MY(
        iconResId = R.drawable.ic_launcher_background,
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
