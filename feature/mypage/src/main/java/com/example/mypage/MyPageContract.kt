package com.example.mypage

import com.example.ui.base.BaseContract
import com.example.ui.model.ScrappedTrackState
import com.example.ui.model.TrackState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class MyPageContract {
    data class MyPageState(
        val isLoading: Boolean = false,
        val userNickname: String = "디플레이",
        val profileImagePath: String? = null,
        val selectedTabIndex: Int = 0,
        val registeredMusicCount: Int = 0,
        val scrappedTrackStateList: ImmutableList<ScrappedTrackState> = dummyBookmarkedTrackStateList,
    ) : BaseContract.State {
    }

    sealed interface MyPageIntent : BaseContract.Intent {
        data object OnSettingIconClick : MyPageIntent

        data object OnProfileClick : MyPageIntent

        data class OnTabClick(
            val tabIndex: Int,
        ) : MyPageIntent

        data class OnMusicItemClick(
            val musicId: Long,
        ) : MyPageIntent

        data class OnKebabIconClick(
            val musicId: Long,
        ) : MyPageIntent

        data object OnBottomSheetDeleteClick : MyPageIntent

        data object OnBottomSheetCancelClick : MyPageIntent

        data object OnDialogDeleteClick : MyPageIntent

        data object OnDialogCancelClick : MyPageIntent
    }

    sealed interface MyPageSideEffect : BaseContract.SideEffect {
        data object NavigateToSettings : MyPageSideEffect

        data object NavigateToEditProfile : MyPageSideEffect

        data class NavigateToDetail(
            val musicId: Long,
        ) : MyPageSideEffect

        data object ShowDeleteBottomSheet : MyPageSideEffect

        data object ShowDeleteDialogue : MyPageSideEffect
    }
}

val dummyBookmarkedTrackStateList =
    persistentListOf(
        ScrappedTrackState(
            postId = 101L,
            track =
                TrackState(
                    trackId = "track_101",
                    musicTitle = "How Sweet",
                    artistName = "NewJeans",
                    thumbnailUrl = "",
                    isrc = ""
                ),
        ),

    )
