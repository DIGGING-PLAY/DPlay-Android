package com.example.setting

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import com.example.navigation.Setting
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object SettingNavigationModule {
    @Provides
    @IntoSet
    fun provideSettingEntry(
        navigator: Navigator,
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<Setting> {
                SettingRoute(
                    navigator = navigator,
                )
            }
        }
}