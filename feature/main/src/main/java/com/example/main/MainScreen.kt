package com.example.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.home.homeScreen
import com.example.mypage.myPageScreen
import com.example.navigation.Route
import com.example.recommend.recommendScreen
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    Scaffold(
        modifier =
            Modifier.navigationBarsPadding(),
        bottomBar = {
            BottomNavigationBar(
                isVisible = navigator.shouldShowBottomBar(),
                mainTabList = MainTab.entries.toImmutableList(),
                currentTab = navigator.currentTab,
                onBottomNavigationItemClick = { navigator.navigateBottomNavigation(it) },
                onPlusButtonClick = { navigator.navigateRecommend() }
            )
        }
    ) { padding ->
        NavHost(
            navController = navigator.navController,
            startDestination = Route.Home,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            homeScreen()
            myPageScreen()
            recommendScreen()
        }
    }
}