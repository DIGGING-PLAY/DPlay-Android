package com.example.onboarding

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph

@Composable
fun OnboardingNavDisplay(
    kakaoAccessToken: String,
    globalNavigator: Navigator,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val onboardingNavigator = remember { Navigator(OnboardingGraph.Terms) }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(OnboardingContract.OnboardingIntent.Initialize(kakaoAccessToken))
    }

    NavDisplay(
        backStack = onboardingNavigator.backStack,
        onBack = {
            if (onboardingNavigator.backStack.size > 1) {
                onboardingNavigator.navigateToBack()
            } else {
                globalNavigator.navigateToBack()
            }
        },
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
                // 1. 약관 동의 화면
                entry<OnboardingGraph.Terms> {
                    OnboardingTermsRoute(
                        onboardingNavigator = onboardingNavigator,
                        globalNavigator = globalNavigator,
                    )
                }

                // 2. 프로필 등록 화면
                entry<OnboardingGraph.Profile> {
                    OnboardingProfileRoute(
                        onboardingNavigator = onboardingNavigator,
                    )
                }

                // 3. 튜토리얼 화면 (Pager 포함)
                entry<OnboardingGraph.Onboarding> {
                    OnboardingRoute(
                        onboardingNavigator = onboardingNavigator,
                        globalNavigator = globalNavigator,
                    )
                }

                // 4. 권한 동의 화면
                entry<OnboardingGraph.Permission> {
                    OnboardingPermissionRoute(
                        onboardingNavigator = onboardingNavigator,
                        globalNavigator = globalNavigator,
                    )
                }
            },
    )
}
