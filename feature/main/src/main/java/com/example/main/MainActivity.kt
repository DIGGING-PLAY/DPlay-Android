package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.designsystem.component.snackbar.DPlaySnackBar
import com.example.designsystem.component.snackbar.LocalShowSnackBar
import com.example.designsystem.component.snackbar.LocalSnackBarState
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Navigator
import com.example.navigation.Search
import com.example.ui.controller.BottomNavigationController
import com.example.ui.controller.LocalBottomNavigationController
import com.example.ui.controller.LocalModalController
import com.example.ui.controller.ModalController
import com.example.ui.handler.AppTerminationHandler
import com.example.ui.handler.GlobalModalHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val modalController = remember { ModalController() }
            val bottomNavigationController = remember { BottomNavigationController() }
            val appTerminationHandler = remember(this) { AppTerminationHandler(this) }
            var snackBarType by remember { mutableStateOf<SnackBarType?>(null) }
            var snackBarAction by remember { mutableStateOf<(() -> Unit)?>(null) }

            CompositionLocalProvider(
                LocalModalController provides modalController,
                LocalBottomNavigationController provides bottomNavigationController,
                LocalSnackBarState provides snackBarType,
                LocalShowSnackBar provides { type, action ->
                    snackBarType = type
                    snackBarAction = action
                },
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
                                AnimatedVisibility(
                                    visible = navigator.shouldShowBottomSheet && bottomNavigationController.bottomNavigationVisible,
                                    enter = fadeIn(animationSpec = tween(100)),
                                    exit = fadeOut(animationSpec = tween(100)),
                                ) {
                                    BottomNavigationBar(
                                        isVisible = true, // AnimatedVisibility가 제어하므로 항상 true
                                        topLevelRouteList = navigator.topLevelRoutes,
                                        currentTab = navigator.currentScreen,
                                        onBottomNavigationItemClick = { route ->
                                            navigator.navigateToTopLevelRoute(route)
                                        },
                                        onPlusButtonClick = {
                                            navigator.navigateTo(Search)
                                        },
                                    )
                                }
                            },
                        ) { padding ->
                            val bottomPadding = if(navigator.shouldShowBottomSheet) padding.calculateBottomPadding() else 0.dp
                            NavDisplay(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = DPlayTheme.colors.dplayWhite)
                                    .padding(bottom = 0.dp),
                                backStack = navigator.backStack,
                                onBack = {
                                    navigator.navigateToBack()
                                },
                                entryDecorators =
                                    listOf(
                                        rememberSaveableStateHolderNavEntryDecorator(),
                                        rememberViewModelStoreNavEntryDecorator(),
                                    ),
                                transitionSpec = {
                                    ContentTransform(
                                        targetContentEnter = EnterTransition.None,
                                        initialContentExit = ExitTransition.None,
                                    )
                                },
                                popTransitionSpec = {
                                    ContentTransform(
                                        targetContentEnter = EnterTransition.None,
                                        initialContentExit = ExitTransition.None,
                                    )
                                },
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
