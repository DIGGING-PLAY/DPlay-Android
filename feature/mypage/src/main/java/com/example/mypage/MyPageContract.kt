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
        val userNickname: String = "디플레이",
        val profileImageUri: Uri? = null,
        val selectedTabIndex: Int = 0,
        val registeredMusicList: ImmutableList<RegisteredMusic> = dummyRegisteredMusicList,
        val bookmarkedMusicList: ImmutableList<BookmarkedMusic> = dummyBookmarkedMusicList,
    ) : BaseContract.State {
        val registeredMusicCount: Int
            get() = registeredMusicList.size
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

val dummyRegisteredMusicList =
    persistentListOf(
        RegisteredMusic(
            id = 1L,
            musicTitle = "Supernova",
            artistName = "aespa",
            comment = "운동할 때 들으면 힘나는 노래!",
            thumbnailUrl = null,
        ),
        RegisteredMusic(
            id = 2L,
            musicTitle = "Magnetic",
            artistName = "ILLIT",
            comment = "요즘 제일 많이 듣는 곡",
            thumbnailUrl = null,
        ),
        RegisteredMusic(
            id = 3L,
            musicTitle = "SPOT! (feat. JENNIE)",
            artistName = "지코 (ZICO)",
            comment = "비트가 너무 좋음",
            thumbnailUrl = null,
        ),
        RegisteredMusic(
            id = 4L,
            musicTitle = "해야 (HEYA)",
            artistName = "IVE (아이브)",
            comment = "반복 재생 중...",
            thumbnailUrl = null,
        ),
        RegisteredMusic(
            id = 5L,
            musicTitle = "소나기",
            artistName = "이클립스 (ECLIPSE)",
            comment = "선재 업고 튀어 OST",
            thumbnailUrl = null,
        ),
        RegisteredMusic(
            id = 6L,
            musicTitle = "Love wins all",
            artistName = "아이유 (IU)",
            comment = "자기 전에 듣기 좋아요 자기 전에 듣기 좋아요 자기 전에 듣기 좋아요",
            thumbnailUrl = null,
        ),
    )

// 2. 보관함 더미 데이터 (13개)
val dummyBookmarkedMusicList =
    persistentListOf(
        BookmarkedMusic(
            id = 101L,
            musicTitle = "How Sweet",
            artistName = "NewJeans",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 102L,
            musicTitle = "Bubble Gum",
            artistName = "NewJeans",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 103L,
            musicTitle = "나는 아픈 건 딱 질색이니까",
            artistName = "(여자)아이들",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 104L,
            musicTitle = "첫 만남은 계획대로 되지 않아",
            artistName = "TWS (투어스)",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 105L,
            musicTitle = "밤양갱",
            artistName = "비비 (BIBI)",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 106L,
            musicTitle = "EASY",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 107L,
            musicTitle = "Smart",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 108L,
            musicTitle = "Drama",
            artistName = "aespa",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 109L,
            musicTitle = "To. X",
            artistName = "태연 (TAEYEON)",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 110L,
            musicTitle = "Perfect Night",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 111L,
            musicTitle = "Love 119",
            artistName = "RIIZE",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 112L,
            musicTitle = "Get A Guitar",
            artistName = "RIIZE",
            thumbnailUrl = null,
        ),
        BookmarkedMusic(
            id = 113L,
            musicTitle = "Ditto",
            artistName = "NewJeans",
            thumbnailUrl = null,
        ),
    )
