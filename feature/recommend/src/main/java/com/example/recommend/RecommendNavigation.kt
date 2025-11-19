package com.example.recommend

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route

fun NavController.navigateToRecommend(navOptions: NavOptions) {
    navigate(
        route = Route.Recommend,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.recommendScreen() {
    composable<Route.Recommend> {
        RecommendScreen()
    }
}
