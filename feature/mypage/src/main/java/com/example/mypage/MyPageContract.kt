package com.example.mypage

import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentSetOf

class MyPageContract {
    data class MyPageState(
        val isLoading: Boolean = false,
        val userNickname: String = "디플레이",
        val profileImagePath: String? = null,
        val selectedTabIndex: Int = 0,
        val registeredMusicCount: Int = -1,
        val isDeleteBottomSheetVisible: Boolean = false,
        val selectedPostId: Long = -1,
        val deletedTrackIds: PersistentSet<Long> = persistentSetOf()
    ) : BaseContract.State

    sealed interface MyPageIntent : BaseContract.Intent {
        data object OnSettingIconClick : MyPageIntent

        data object OnProfileClick : MyPageIntent

        data class OnTabClick(
            val tabIndex: Int,
        ) : MyPageIntent

        data class OnScrappedTrackClick(
            val postId: Long,
        ) : MyPageIntent

        data class OnKebabIconClick(
            val musicId: Long,
        ) : MyPageIntent

        data class OnRegisteredTrackClick(
            val postId: Long,
        ): MyPageIntent

        data object OnBottomSheetDeleteClick : MyPageIntent

        data object OnBottomSheetCancelClick : MyPageIntent

        data object OnDialogDeleteClick : MyPageIntent
    }

    sealed interface MyPageSideEffect : BaseContract.SideEffect {
        data object NavigateToSettings : MyPageSideEffect

        data object NavigateToEditProfile : MyPageSideEffect

        data class NavigateToDetail(
            val postId: Long,
        ) : MyPageSideEffect

        data object ShowDeleteDialogue : MyPageSideEffect

        data object HideBottomNavigation : MyPageSideEffect

        data object ShowBottomNavigation : MyPageSideEffect
    }
}
