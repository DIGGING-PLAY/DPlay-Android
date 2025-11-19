package com.example.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(
        route = Route.Home,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.homeScreen() {
    composable<Route.Home> {
        HomeScreen()
    }
}
