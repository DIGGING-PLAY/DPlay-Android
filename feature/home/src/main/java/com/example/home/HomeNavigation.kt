package com.example.home

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Home
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeNavigationModule {
    @Provides
    @IntoSet
    fun provideHomeEntry(): EntryProviderScope<NavKey>.() -> Unit  = {
        entry<Home> {
            HomeScreen()
        }
    }
}