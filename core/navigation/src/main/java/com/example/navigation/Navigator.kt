package com.example.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import timber.log.Timber

@ActivityRetainedScoped
class Navigator(
    startDestination: NavKey,
) {
    val backStack: SnapshotStateList<NavKey> = mutableStateListOf(startDestination)

    val currentScreen: NavKey?
        get() = backStack.lastOrNull()

    val shouldShowBottomSheet: Boolean
        get() = backStack.lastOrNull() is TopLevelRoute

    val topLevelRoutes: ImmutableList<TopLevelRoute> = persistentListOf(Home, MyPage)

    fun navigateToTopLevelRoute(destination: TopLevelRoute) {
        backStack.clear()
        backStack.add(destination as NavKey)
        Timber.d("backStack: $backStack")
    }

    fun navigateTo(destination: NavKey) {
        backStack.add(destination)
        Timber.d("backStack: $backStack")
    }

    fun navigateToBack() {
        backStack.removeLastOrNull()
        Timber.d("backStack: $backStack")
    }
}
