package com.example.search

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import com.example.navigation.Search
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object SearchNavigationModule {
    @Provides
    @IntoSet
    fun provideSearchEntry(
        navigator: Navigator
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<Search> {
                SearchRoute(navigator = navigator)
            }
        }
}