package com.example.comment

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.navigation.Comment
import com.example.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
object CommentNavigationModule {
    @Provides
    @IntoSet
    fun provideCommentEntry(
        navigator: Navigator,
    ): EntryProviderScope<NavKey>.() -> Unit =
        {
            entry<Comment> { key ->
                CommentRoute(
                    musicInfo = key.musicInfo,
                    navigator = navigator,
                )
            }
        }
}
