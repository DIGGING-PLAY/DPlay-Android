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
            SearchContract.SearchIntent.OnBackIconClick -> TODO()
            is SearchContract.SearchIntent.OnMusicSelected -> TODO()
            SearchContract.SearchIntent.OnNextButtonClick -> TODO()
            is SearchContract.SearchIntent.OnSearchInputChanged -> TODO()
        }
    }

}