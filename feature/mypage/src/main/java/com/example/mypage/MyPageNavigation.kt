package com.example.mypage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(
        route = Route.MyPage,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.myPageScreen() {
    composable<Route.MyPage> {
        MyPageScreen()
    }
}
