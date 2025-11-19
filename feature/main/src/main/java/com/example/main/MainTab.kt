package com.example.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.dplay.designsystem.R
import com.example.navigation.Route

// 바텀 네비게이션 탭 정의
enum class MainTab(
    val route: Route,
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
) {
    HOME(
        route = Route.Home,
        selectedIconRes = R.drawable.ic_home_active_32,
        unselectedIconRes = R.drawable.ic_home_disabled_32
    ),
    MY_PAGE(
        route = Route.MyPage,
        selectedIconRes = R.drawable.ic_bookmark_active_32,
        unselectedIconRes = R.drawable.ic_bookmark_disabled_32
    );

    companion object {
        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }

        @Composable
        fun find(predicate: @Composable (Route) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }
    }
}