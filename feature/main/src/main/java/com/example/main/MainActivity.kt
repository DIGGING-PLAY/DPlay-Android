package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Home
import com.example.navigation.MyPage
import com.example.navigation.Navigator
import com.example.navigation.Recommend
import com.example.navigation.TopLevelRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

private val TOP_LEVEL_ROUTES : ImmutableList<TopLevelRoute> = persistentListOf(Home, MyPage)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigator = remember { Navigator(Home) }
            DPlayTheme {
                Scaffold(
                    modifier =
                        Modifier.navigationBarsPadding(),
                    bottomBar = {
                        BottomNavigationBar(
                            isVisible = navigator.shouldShowBottomSheet,
                            topLevelRouteList = TOP_LEVEL_ROUTES,
                            currentTab = navigator.currentScreen,
                            onBottomNavigationItemClick = { route ->
                                navigator.goToTopLevelRoute(route)
                            },
                            onPlusButtonClick = {
                                navigator.goTo(Recommend)
                            },
                        )
                    },
                ) { padding ->
                    NavDisplay(
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                        backStack = navigator.backStack,
                        onBack = { navigator.goBack() },
                        entryProvider = entryProvider {
                            entryProviders.forEach { installer ->
                                installer()
                            }
                        }
                    )
                }
            }
        }
    }
}