package com.example.detail

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Detail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object DetailNavigationModule {
    @Provides
    @IntoSet
    fun provideDetailEntry(): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<Detail> {
                DetailRoute()
            }
        }
}
