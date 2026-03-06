package com.example.onboarding

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import com.example.navigation.OnboardingGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object OnboardingNavigationModule {
    @Provides
    @IntoSet
    fun provideOnboardingEntries(
        navigator: Navigator,
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<OnboardingGraph> { key ->
                OnboardingNavDisplay(
                    kakaoAccessToken = key.kakaoAccessToken,
                    globalNavigator = navigator,
                )
            }
        }
}
