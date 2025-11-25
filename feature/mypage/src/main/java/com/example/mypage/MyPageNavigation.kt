package com.example.mypage

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.MyPage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object MyPageNavigationModule {
    @Provides
    @IntoSet
    fun provideMyPageEntry(): EntryProviderScope<NavKey>.() -> Unit = {
        entry<MyPage> {
            MyPageScreen()
        }
    }
}