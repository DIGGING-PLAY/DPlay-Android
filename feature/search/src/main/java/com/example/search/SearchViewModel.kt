package com.example.search

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject constructor(
    ) : BaseViewModel<SearchContract.SearchState, SearchContract.SearchIntent, SearchContract.SearchSideEffect>(
    SearchContract.SearchState()
    ){
    override fun handleIntent(intent: SearchContract.SearchIntent) {
        when(intent){
            SearchContract.SearchIntent.OnBackIconClick -> {
                setSideEffect(SearchContract.SearchSideEffect.NavigateToBack)
            }
            is SearchContract.SearchIntent.OnMusicSelected -> {
                updateState {
                    copy(
                        selectedMusic = intent.music
                    )
                }
            }
            SearchContract.SearchIntent.OnNextButtonClick -> {
                setSideEffect(SearchContract.SearchSideEffect.NavigateToComment(uiState.value.selectedMusic!!))
            }
            is SearchContract.SearchIntent.OnSearchInputChanged -> {
                updateState {
                    copy(
                        searchInput = intent.input
                    )
                }
            }
        }
    }

}