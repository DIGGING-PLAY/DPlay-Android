package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Navigator
import com.example.navigation.Recommend
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DPlayTheme {
                Scaffold(
                    modifier =
                        Modifier.navigationBarsPadding(),
                    bottomBar = {
                        BottomNavigationBar(
                            isVisible = navigator.shouldShowBottomSheet,
                            topLevelRouteList = navigator.topLevelRoutes,
                            currentTab = navigator.currentScreen,
                            onBottomNavigationItemClick = { route ->
                                navigator.navigateToTopLevelRoute(route)
                            },
                            onPlusButtonClick = {
                                navigator.navigateTo(Recommend)
                            },
                        )
                    },
                ) { padding ->
                    NavDisplay(
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                        backStack = navigator.backStack,
                        onBack = { navigator.navigateToBack() },
                        entryDecorators =
                            listOf(
                                rememberSaveableStateHolderNavEntryDecorator(),
                                rememberViewModelStoreNavEntryDecorator(),
                            ),
                        entryProvider =
                            entryProvider {
                                entryProviders.forEach { installer ->
                                    installer()
                                }
                            },
                    )
                }
            }
        }
    }
}
