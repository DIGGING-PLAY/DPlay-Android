package com.example.navigation

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.NavKey
import com.dplay.designsystem.R
import kotlinx.parcelize.Parcelize

sealed interface TopLevelRoute {
    @get:DrawableRes
    val selectedIconRes: Int

    @get:DrawableRes
    val unselectedIconRes: Int
}

data object Home : TopLevelRoute, NavKey {
    override val selectedIconRes: Int
        get() = R.drawable.ic_home_active_32
    override val unselectedIconRes: Int
        get() = R.drawable.ic_home_disabled_32
}

data object MyPage : TopLevelRoute, NavKey {
    override val selectedIconRes: Int
        get() = R.drawable.ic_bookmark_active_32
    override val unselectedIconRes: Int
        get() = R.drawable.ic_bookmark_disabled_32
}

data object Splash : NavKey

data object Login : NavKey

data object OnboardingGraph : NavKey {
    data object Terms : NavKey

    data object Profile : NavKey

    data object Onboarding : NavKey

    data object Permission : NavKey
}

data object Recommend : NavKey

data object Setting : NavKey

data object EditProfile : NavKey

@Parcelize
data class Detail(
    val postId: Long,
) : NavKey,
    Parcelable
