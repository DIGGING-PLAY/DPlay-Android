package com.example.search

import com.example.ui.base.BaseContract
import com.example.ui.model.Music
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class SearchContract {
    data class SearchState(
        val searchInput: String = "",
        val searchedMusicList: ImmutableList<Music> = dummyMusicList,
        val selectedMusicId: String? = null,
    ): BaseContract.State{
        val isSearchIconEnabled: Boolean
            get() = searchInput.isNotEmpty()

        val isNextButtonEnabled: Boolean
            get() = selectedMusicId != null
    }

    sealed interface SearchIntent : BaseContract.Intent {
        data class OnSearchInputChanged(
            val input: String,
        ) : SearchIntent

        data object OnNextButtonClick : SearchIntent

        data object OnBackIconClick : SearchIntent

        data class OnMusicSelected(
            val trackId: String,
        ) : SearchIntent
    }

    sealed interface SearchSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : SearchSideEffect

        data class NavigateToComment(val trackId: String) : SearchSideEffect
    }
}

val dummyMusicList = persistentListOf(
    Music(
        trackId = "track_201",
        musicTitle = "SOLO",
        artistName = "제니 (JENNIE)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_202",
        musicTitle = "Dynamite",
        artistName = "방탄소년단 (BTS)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_203",
        musicTitle = "How You Like That",
        artistName = "BLACKPINK",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_204",
        musicTitle = "라일락 (Lilac)",
        artistName = "아이유 (IU)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_205",
        musicTitle = "한 페이지가 될 수 있게",
        artistName = "DAY6 (데이식스)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_206",
        musicTitle = "Love Lee",
        artistName = "AKMU (악뮤)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_207",
        musicTitle = "UNFORGIVEN (feat. Nile Rodgers)",
        artistName = "LE SSERAFIM (르세라핌)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_208",
        musicTitle = "I AM",
        artistName = "IVE (아이브)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_209",
        musicTitle = "DASH",
        artistName = "NMIXX (엔믹스)",
        thumbnailUrl = null
    ),
    Music(
        trackId = "track_210",
        musicTitle = "손오공",
        artistName = "세븐틴 (SEVENTEEN)",
        thumbnailUrl = null
    )
)