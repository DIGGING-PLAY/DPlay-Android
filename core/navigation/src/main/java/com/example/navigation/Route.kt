package com.example.navigation

import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.NavKey
import com.dplay.designsystem.R
import com.example.domain.model.BADGE
import com.example.ui.model.TrackState
import kotlinx.serialization.Serializable

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

enum class MyPageTab {
    REGISTERED,
    BOOKMARKED,
}

data class MyPage(
    val initialTab: MyPageTab = MyPageTab.REGISTERED,
) : TopLevelRoute,
    NavKey {
    override val selectedIconRes: Int
        get() = R.drawable.ic_bookmark_active_32
    override val unselectedIconRes: Int
        get() = R.drawable.ic_bookmark_disabled_32
}

data object Splash : NavKey

data object Login : NavKey

data class OnboardingGraph(
    val kakaoAccessToken: String,
) : NavKey {
    data object Terms : NavKey

    data object Profile : NavKey

    data object Onboarding : NavKey

    data object Permission : NavKey
}

data object Recommend : NavKey

data object Search : NavKey

data class Comment(
    val track: TrackState,
) : NavKey

data object Setting : NavKey

data object EditProfile : NavKey

@Serializable
data object Record : NavKey

@Serializable
data class Detail(
    val postId: Long,
    val badge: BADGE? = null,
) : NavKey

@Serializable
data class OtherProfile(
    val userId: Long,
) : NavKey
