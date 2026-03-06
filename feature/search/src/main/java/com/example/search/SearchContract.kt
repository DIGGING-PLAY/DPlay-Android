package com.example.search

import com.example.ui.base.BaseContract
import com.example.ui.model.TrackState

class SearchContract {
    data class SearchState(
        val searchInput: String = "",
        val selectedTrack: TrackState? = null,
    ) : BaseContract.State {
        val isSearchIconEnabled: Boolean
            get() = searchInput.isNotEmpty()

        val isNextButtonEnabled: Boolean
            get() = selectedTrack != null
    }

    sealed interface SearchIntent : BaseContract.Intent {
        data class OnSearchInputChanged(
            val input: String,
        ) : SearchIntent

        data object OnNextButtonClick : SearchIntent

        data object OnBackIconClick : SearchIntent

        data class OnMusicSelected(
            val track: TrackState,
        ) : SearchIntent
    }

    sealed interface SearchSideEffect : BaseContract.SideEffect {
        data object NavigateToBack : SearchSideEffect

        data class NavigateToComment(
            val track: TrackState,
        ) : SearchSideEffect
    }
}
