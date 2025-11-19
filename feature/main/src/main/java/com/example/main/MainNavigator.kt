package com.example.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.home.navigateToHome
import com.example.mypage.navigateToMyPage
import com.example.recommend.navigateToRecommend
import timber.log.Timber

class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() {
            val destination = currentDestination
            return MainTab.find { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val destination = currentDestination
        val shouldShow = MainTab.contains {
            Timber.d("MainNavigator", "currentDestinationInLambda: $currentDestination")
            Timber.d("MainNavigator", "localValue: $destination")
            destination?.hasRoute(it::class) == true
        }
        Timber.d("MainNavigator", "currentDestination: ${destination?.route}, shouldShow: $shouldShow")
        return shouldShow
    }

    fun navigateBottomNavigation(tab: MainTab) {
        Timber.d("MainNavigator", "Navigating to ${tab.name}")
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.MY_PAGE -> navController.navigateToMyPage(navOptions)
        }
    }

    fun navigateRecommend(){
        navController.navigateToRecommend(
            navOptions = navOptions {
                launchSingleTop = true
            }
        )
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}