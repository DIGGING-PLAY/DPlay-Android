package com.example.login

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Login
import com.example.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginNavigationModule {
    @Provides
    @IntoSet
    fun provideLoginEntry(
        navigator: Navigator,
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<Login> {
                LoginRoute(
                    navigator = navigator,
                )
            }
        }
}
