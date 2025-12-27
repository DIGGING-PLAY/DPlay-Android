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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayImageCheck
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayLargePinkButton
import com.example.designsystem.component.textfield.DPlayTextInput
import com.example.designsystem.theme.DPlayTheme
import com.example.ui.model.Music
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SearchRoute(modifier: Modifier = Modifier) {
    SearchScreen(
        state = SearchContract.SearchState(),
        modifier = modifier
    )
}

@Composable
fun SearchScreen(
    state: SearchContract.SearchState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = DPlayTheme.colors.dplayWhite)
            .padding(bottom = 16.dp)
    ){
        DplayLeftIconTitleTopAppBar(
            title = "노래 등록하기"
        ) {  }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "추천하고 싶은\n노래를 검색해보세요!",
            style = DPlayTheme.typography.titleBold24,
            color = DPlayTheme.colors.dplayBlack,
            modifier = Modifier.padding(start = 17.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        DPlayTextInput(
            value = state.searchInput,
            onValueChange = { },
            placeholder = stringResource(R.string.placeholder_music_search),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchedMusicList(
            searchedMusicList = state.searchedMusicList,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        DPlayLargePinkButton(
            onClick = {},
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
    selectedTrackId: String? = null,
    modifier: Modifier = Modifier
) {
    if(searchedMusicList.isEmpty()){
        // emptyView
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(153.dp))

            Text(
                text = "일치하는 검색 결과가 없어요"
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
                    onClick = {},
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