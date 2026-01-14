package com.example.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.repository.TrackRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.model.Music
import com.example.ui.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val trackRepository: TrackRepository,
    ) : BaseViewModel<SearchContract.SearchState, SearchContract.SearchIntent, SearchContract.SearchSideEffect>(
            SearchContract.SearchState(),
        ) {

        @OptIn(FlowPreview::class)
        val searchResults: Flow<PagingData<Music>> = uiState
            .map { it.searchInput }
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .debounce(300L)
            .flatMapLatest { query ->
                trackRepository.searchTracks(query).map { pagingData ->
                    pagingData.map { track ->
                        track.toUiState()
                    }
                }
            }
            .cachedIn(viewModelScope)

        override fun handleIntent(intent: SearchContract.SearchIntent) {
            when (intent) {
                SearchContract.SearchIntent.OnBackIconClick -> {
                    setSideEffect(SearchContract.SearchSideEffect.NavigateToBack)
                }
                is SearchContract.SearchIntent.OnMusicSelected -> {
                    updateState {
                        copy(
                            selectedMusic = intent.music,
                        )
                    }
                }
                SearchContract.SearchIntent.OnNextButtonClick -> {
                    setSideEffect(SearchContract.SearchSideEffect.NavigateToComment(uiState.value.selectedMusic!!))
                }
                is SearchContract.SearchIntent.OnSearchInputChanged -> {
                    updateState {
                        copy(
                            searchInput = intent.input,
                        )
                    }
                }
            }
        }
    }
