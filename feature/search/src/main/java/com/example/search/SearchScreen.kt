package com.example.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayImageCheck
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Comment
import com.example.navigation.Navigator
import com.example.ui.model.Music
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SearchRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.sideEffect.collect{ sideEffect ->
            when(sideEffect){
                SearchContract.SearchSideEffect.NavigateToBack -> {
                    navigator.navigateToBack()
                }
                is SearchContract.SearchSideEffect.NavigateToComment -> {
                    navigator.navigateTo(Comment(sideEffect.musicInfo))
                }
            }
        }
    }

    SearchScreen(
        state = state,
        modifier = modifier,
        onBackIconClick = { viewModel.handleIntent(SearchContract.SearchIntent.OnBackIconClick) },
        onSearchInputChanged = { viewModel.handleIntent(SearchContract.SearchIntent.OnSearchInputChanged(it)) },
        onMusicSelected = { viewModel.handleIntent(SearchContract.SearchIntent.OnMusicSelected(it)) },
        onNextButtonClick = { viewModel.handleIntent(SearchContract.SearchIntent.OnNextButtonClick) }
    )
}

@Composable
fun SearchScreen(
    state: SearchContract.SearchState,
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
    onSearchInputChanged: (String) -> Unit = {},
    onMusicSelected: (Music) -> Unit = {},
    onNextButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(bottom = 16.dp)
    ){
        DplayLeftIconTitleTopAppBar(
            title = stringResource(com.dplay.search.R.string.search_top_bar_title)
        ) {  onBackIconClick() }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(com.dplay.search.R.string.search_title),
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            modifier = Modifier.padding(start = 17.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        DPlayTextInput(
            value = state.searchInput,
            onValueChange = { onSearchInputChanged(it) },
            placeholder = stringResource(R.string.placeholder_music_search),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchedMusicList(
            searchedMusicList = state.searchedMusicList,
            onMusicSelected = { onMusicSelected(it) },
            selectedTrackId = state.selectedMusic?.trackId,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        DPlayLargePinkButton(
            onClick = { onNextButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = stringResource(R.string.next_button_label),
            enabled = state.isNextButtonEnabled
        )
    }
}

@Composable
private fun SearchedMusicList(
    searchedMusicList: ImmutableList<Music>,
    onMusicSelected: (Music) -> Unit,
    modifier: Modifier = Modifier,
    selectedTrackId: String? = null
) {
    if(searchedMusicList.isEmpty()){
        // emptyView
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(153.dp))

            Text(
                text = stringResource(com.dplay.search.R.string.search_empty_view_text)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }else{
        LazyColumn(
            modifier = modifier
        ){
            items(
                items = searchedMusicList,
                key = { it.trackId }
            ){ music ->
                // url이 null로 왔을 때 기본 이미지 필요
                DPlayImageCheck(
                    imageUrl = music.thumbnailUrl?: "",
                    musicName = music.musicTitle,
                    artistName = music.artistName,
                    isChecked = selectedTrackId == music.trackId,
                    onClick = { onMusicSelected(music) },
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        SearchScreen(
            state = SearchContract.SearchState(
                searchedMusicList = dummyMusicList
            ),
        )
    }
}