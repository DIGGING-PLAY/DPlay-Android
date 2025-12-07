package com.example.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import com.example.navigation.Onboarding
import com.example.navigation.OnboardingPermission
import com.example.navigation.OnboardingProfile
import com.example.navigation.OnboardingTerms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class) // ViewModel과 수명 주기를 맞춤
object OnboardingNavigationModule {

    @Provides
    @IntoSet
    fun provideOnboardingEntries(
        navigator: Navigator
    ): EntryProviderScope<NavKey>.() -> Unit = {

        // 1. 약관 동의 화면
        entry<OnboardingTerms> {
            OnboardingTermsRoute(
                navigator = navigator
            )
        }

        // 2. 프로필 등록 화면
        entry<OnboardingProfile> {
            OnboardingProfileRoute(
                navigator = navigator
            )
        }

        // 3. 튜토리얼 화면 (Pager 포함)
        entry<Onboarding> {
            OnboardingRoute(
                navigator = navigator
            )
        }

        // 4. 권한 동의 화면
        entry<OnboardingPermission> {
            OnboardingPermissionRoute(
                navigator = navigator
            )
        }
    }
}