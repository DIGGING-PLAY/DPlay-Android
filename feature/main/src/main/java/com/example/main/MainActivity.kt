package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.designsystem.component.snackbar.DPlaySnackBar
import com.example.designsystem.component.snackbar.LocalShowSnackBar
import com.example.designsystem.component.snackbar.LocalSnackBarState
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Home
import com.example.navigation.MyPage
import com.example.navigation.Navigator
import com.example.navigation.Recommend
import com.example.navigation.TopLevelRoute
import com.example.navigation.Navigator
import com.example.navigation.Recommend
import com.example.ui.controller.LocalModalController
import com.example.ui.controller.ModalController
import com.example.ui.handler.AppTerminationHandler
import com.example.ui.handler.GlobalModalHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

private val TOP_LEVEL_ROUTES: ImmutableList<TopLevelRoute> = persistentListOf(Home, MyPage)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modalController = remember { ModalController() }
            val appTerminationHandler = remember(this) { AppTerminationHandler(this) }

            CompositionLocalProvider(
                LocalModalController provides modalController,
            ) {
                DPlayTheme {
                    BackHandler(enabled = navigator.backStack.size <= 1) {
                        appTerminationHandler.onBackPress()
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
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
                                onBack = {
                                    navigator.navigateToBack()
                                },
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

                        GlobalModalHandler(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
            val navigator = remember { Navigator(Home) }
            var snackBarType by remember { mutableStateOf<SnackBarType?>(null) }
            var snackBarAction by remember { mutableStateOf<(() -> Unit)?>(null) }

            CompositionLocalProvider(
                LocalSnackBarState provides snackBarType,
                LocalShowSnackBar provides { type, action ->
                    snackBarType = type
                    snackBarAction = action
                },
            ) {
                DPlayTheme {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Scaffold(
                            modifier = Modifier.navigationBarsPadding(),
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
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(color = DPlayTheme.colors.dplayWhite)
                                        .padding(bottom = padding.calculateBottomPadding()),
                                backStack = navigator.backStack,
                                onBack = { navigator.goBack() },
                                entryProvider =
                                    entryProvider {
                                        entryProviders.forEach { installer ->
                                            installer()
                                        }
                                    },
                            )
                        }

                        snackBarType?.let { type ->
                            DPlaySnackBar(
                                type = type,
                                onActionClick = {
                                    snackBarAction?.invoke()
                                    snackBarType = null
                                    snackBarAction = null
                                },
                                onDismiss = {
                                    snackBarType = null
                                    snackBarAction = null
                                },
                                modifier =
                                    Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 80.dp, start = 16.dp, end = 16.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
