package com.example.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.navigation.Navigator
import com.example.navigation.Onboarding
import com.example.navigation.OnboardingPermission
import com.example.navigation.OnboardingProfile
import com.example.navigation.OnboardingTerms

@Composable
fun OnboardingNavDisplay(
    globalNavigator: Navigator,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val onboardingNavigator = remember { Navigator(OnboardingTerms) }

    NavDisplay(
        backStack = onboardingNavigator.backStack,
        onBack = {
            if (onboardingNavigator.backStack.size > 1) {
                onboardingNavigator.goBack()
            } else {
                globalNavigator.goBack()
            }
        },
        entryProvider = entryProvider {
            // 1. 약관 동의 화면
            entry<OnboardingTerms> {
                OnboardingTermsRoute(
                    onboardingNavigator = onboardingNavigator,
                    globalNavigator = globalNavigator
                )
            }

            // 2. 프로필 등록 화면
            entry<OnboardingProfile> {
                OnboardingProfileRoute(
                    onboardingNavigator = onboardingNavigator
                )
            }

            // 3. 튜토리얼 화면 (Pager 포함)
            entry<Onboarding> {
                OnboardingRoute(
                    onboardingNavigator = onboardingNavigator
                )
            }

            // 4. 권한 동의 화면
            entry<OnboardingPermission> {
                OnboardingPermissionRoute(
                    onboardingNavigator = onboardingNavigator,
                    globalNavigator = globalNavigator
                )
            }
        }
    )
}