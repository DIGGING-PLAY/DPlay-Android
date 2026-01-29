package com.example.otherprofile

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import com.example.navigation.OtherProfile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object OtherProfileNavigationModule {
    @Provides
    @IntoSet
    fun provideOtherProfileEntry(
        navigator: Navigator,
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<OtherProfile> { myPage ->
                OtherProfileRoute(
                    navigator = navigator,
                    userId = myPage.userId,
                )
            }
        }
}
