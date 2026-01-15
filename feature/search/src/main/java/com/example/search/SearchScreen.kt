package com.example.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayImageCheck
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Comment
import com.example.navigation.Navigator
import com.example.ui.emptyLazyPagingItems
import com.example.ui.model.TrackState

@Composable
fun SearchRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pagingSearchedMusics = viewModel.searchResults.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SearchContract.SearchSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                is SearchContract.SearchSideEffect.NavigateToComment -> {
                    navigator.navigateTo(Comment(sideEffect.track))
                }
            }
        }
    }

    SearchScreen(
        state = state,
        searchedTrackList = pagingSearchedMusics,
        modifier = modifier,
        onBackIconClick = { viewModel.handleIntent(SearchContract.SearchIntent.OnBackIconClick) },
        onSearchInputChanged = { viewModel.handleIntent(SearchContract.SearchIntent.OnSearchInputChanged(it)) },
        onMusicSelected = { viewModel.handleIntent(SearchContract.SearchIntent.OnMusicSelected(it)) },
        onNextButtonClick = { viewModel.handleIntent(SearchContract.SearchIntent.OnNextButtonClick) },
    )
}

@Composable
fun SearchScreen(
    state: SearchContract.SearchState,
    searchedTrackList: LazyPagingItems<TrackState>,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onSearchInputChanged: (String) -> Unit = {},
    onMusicSelected: (TrackState) -> Unit = {},
    onNextButtonClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.dplayWhite)
                .padding(bottom = 16.dp),
    ) {
        DplayLeftIconTitleTopAppBar(
            title = stringResource(com.dplay.search.R.string.search_top_bar_title),
        ) { onBackIconClick() }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(com.dplay.search.R.string.search_title),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            modifier = Modifier.padding(start = 17.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        DPlayTextInput(
            value = state.searchInput,
            onValueChange = { onSearchInputChanged(it) },
            placeholder = stringResource(R.string.placeholder_music_search),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchedMusicList(
            searchedTrackList = searchedTrackList,
            onMusicSelected = { onMusicSelected(it) },
            selectedTrackId = state.selectedTrack?.trackId,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.height(12.dp))

        DPlayLargePinkButton(
            onClick = { onNextButtonClick() },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            label = stringResource(R.string.next_button_label),
            enabled = state.isNextButtonEnabled,
        )
    }
}

@Composable
private fun SearchedMusicList(
    searchedTrackList: LazyPagingItems<TrackState>,
    onMusicSelected: (TrackState) -> Unit,
    modifier: Modifier = Modifier,
    selectedTrackId: String? = null,
) {
    if (searchedTrackList.itemCount == 0) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(153.dp))

            Text(
                text = stringResource(com.dplay.search.R.string.search_empty_view_text),
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    } else {
        LazyColumn(
            modifier = modifier,
        ) {
            items(
                count = searchedTrackList.itemCount,
                key = searchedTrackList.itemKey { it.trackId }
            ) { index ->

                val music = searchedTrackList[index]
                if(music != null){
                    DPlayImageCheck(
                        imageUrl = music.thumbnailUrl,
                        musicName = music.musicTitle,
                        artistName = music.artistName,
                        isChecked = selectedTrackId == music.trackId,
                        onClick = { onMusicSelected(music) },
                    )
                }else {
                    // 오류처리
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        SearchScreen(
            state = SearchContract.SearchState(),
            searchedTrackList = emptyLazyPagingItems(),
        )
    }
}
