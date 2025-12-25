package com.example.mypage

import android.net.Uri
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RegisteredMusic(
    val id: Long,
    val musicTitle: String,
    val artistName: String,
    val comment: String,
    val thumbnailUrl: String?,
)

data class BookmarkedMusic(
    val id: Long,
    val musicTitle: String,
    val artistName: String,
    val thumbnailUrl: String?,
)

class MyPageContract {
    data class MyPageState(
        val isLoading: Boolean = false,
        val userNickname: String = "",
        val profileImageUri: Uri? = null,
        val selectedTabIndex: Int = 0,
        val registeredMusicList: ImmutableList<RegisteredMusic> = persistentListOf(),
        val bookmarkedMusicList: ImmutableList<BookmarkedMusic> = persistentListOf(),
    ): BaseContract.State{
        val registeredMusicCount: Int
            get() = registeredMusicList.size

        val bookmarkedMusicCount: Int
            get() = bookmarkedMusicList.size
    }

    sealed interface MyPageIntent : BaseContract.Intent {
        data object OnSettingIconClick: MyPageIntent

        data object OnProfileClick: MyPageIntent

        data class OnTabClick(val tabIndex: Int): MyPageIntent

        data class OnMusicItemClick(val musicId: Long): MyPageIntent

        data class OnKebabIconClick(val musicId: Long): MyPageIntent

        data object OnBottomSheetDeleteClick: MyPageIntent

        data object OnBottomSheetCancelClick: MyPageIntent

        data object OnDialogueDeleteClick: MyPageIntent

        data object OnDialogueCancelClick: MyPageIntent
    }

    sealed interface MyPageSideEffect : BaseContract.SideEffect {
        data object NavigateToSettings : MyPageSideEffect

        data object NavigateToEditProfile : MyPageSideEffect

        data class NavigateToDetail(val musicId: Long) : MyPageSideEffect

        data object ShowDeleteBottomSheet : MyPageSideEffect

        data object ShowDeleteDialogue : MyPageSideEffect
    }
}