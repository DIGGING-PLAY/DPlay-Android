package com.example.recommend

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Recommend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object RecommendNavigationModule {
    @Provides
    @IntoSet
    fun provideRecommendEntry(): EntryProviderScope<NavKey>.() -> Unit = {
        entry<Recommend> {
            RecommendScreen()
        }
    }
}