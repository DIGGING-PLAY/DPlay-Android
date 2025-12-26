package com.example.mypage

import android.net.Uri
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RegisteredMusic(
    val postId: Long,
    val comment: String,
    val music: Music
)

data class Music(
    val trackId: String,
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
        val bookmarkedMusicList: ImmutableList<RegisteredMusic> = dummyBookmarkedMusicList,
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

val dummyRegisteredMusicList = persistentListOf(
    RegisteredMusic(
        postId = 1L,
        comment = "운동할 때 들으면 힘나는 노래!",
        music = Music(
            trackId = "track_1",
            musicTitle = "Supernova",
            artistName = "aespa",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 2L,
        comment = "요즘 제일 많이 듣는 곡",
        music = Music(
            trackId = "track_2",
            musicTitle = "Magnetic",
            artistName = "ILLIT",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 3L,
        comment = "비트가 너무 좋음",
        music = Music(
            trackId = "track_3",
            musicTitle = "SPOT! (feat. JENNIE)",
            artistName = "지코 (ZICO)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 4L,
        comment = "반복 재생 중...",
        music = Music(
            trackId = "track_4",
            musicTitle = "해야 (HEYA)",
            artistName = "IVE (아이브)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 5L,
        comment = "선재 업고 튀어 OST",
        music = Music(
            trackId = "track_5",
            musicTitle = "소나기",
            artistName = "이클립스 (ECLIPSE)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 6L,
        comment = "자기 전에 듣기 좋아요 자기 전에 듣기 좋아요 자기 전에 듣기 좋아요",
        music = Music(
            trackId = "track_6",
            musicTitle = "Love wins all",
            artistName = "아이유 (IU)",
            thumbnailUrl = null
        )
    )
)

val dummyBookmarkedMusicList = persistentListOf(
    RegisteredMusic(
        postId = 101L,
        comment = "", // 보관함용 빈 코멘트
        music = Music(
            trackId = "track_101",
            musicTitle = "How Sweet",
            artistName = "NewJeans",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 102L,
        comment = "",
        music = Music(
            trackId = "track_102",
            musicTitle = "Bubble Gum",
            artistName = "NewJeans",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 103L,
        comment = "",
        music = Music(
            trackId = "track_103",
            musicTitle = "나는 아픈 건 딱 질색이니까",
            artistName = "(여자)아이들",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 104L,
        comment = "",
        music = Music(
            trackId = "track_104",
            musicTitle = "첫 만남은 계획대로 되지 않아",
            artistName = "TWS (투어스)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 105L,
        comment = "",
        music = Music(
            trackId = "track_105",
            musicTitle = "밤양갱",
            artistName = "비비 (BIBI)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 106L,
        comment = "",
        music = Music(
            trackId = "track_106",
            musicTitle = "EASY",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 107L,
        comment = "",
        music = Music(
            trackId = "track_107",
            musicTitle = "Smart",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 108L,
        comment = "",
        music = Music(
            trackId = "track_108",
            musicTitle = "Drama",
            artistName = "aespa",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 109L,
        comment = "",
        music = Music(
            trackId = "track_109",
            musicTitle = "To. X",
            artistName = "태연 (TAEYEON)",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 110L,
        comment = "",
        music = Music(
            trackId = "track_110",
            musicTitle = "Perfect Night",
            artistName = "LE SSERAFIM",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 111L,
        comment = "",
        music = Music(
            trackId = "track_111",
            musicTitle = "Love 119",
            artistName = "RIIZE",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 112L,
        comment = "",
        music = Music(
            trackId = "track_112",
            musicTitle = "Get A Guitar",
            artistName = "RIIZE",
            thumbnailUrl = null
        )
    ),
    RegisteredMusic(
        postId = 113L,
        comment = "",
        music = Music(
            trackId = "track_113",
            musicTitle = "Ditto",
            artistName = "NewJeans",
            thumbnailUrl = null
        )
    )
)
