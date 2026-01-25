package com.example.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.dplay.record.R
import com.example.designsystem.component.DPlayMusicListItem
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.theme.DPlayTheme
import com.example.domain.model.BADGE
import com.example.domain.model.FeedItem
import com.example.ui.emptyLazyPagingItems

@Composable
fun RecordListScreen(
    onBackButtonClick: () -> Unit,
    onMusicClick: (postId: Long) -> Unit,
    modifier: Modifier = Modifier,
    uiState: RecordContract.RecordState = RecordContract.RecordState(),
    questionPosts: LazyPagingItems<FeedItem> = emptyLazyPagingItems(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        DplayLeftIconTitleTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = uiState.selectedQuestion!!.recordMMDD,
            onLeftClick = onBackButtonClick,
        )
        Spacer(modifier = Modifier.height(12.dp))

        DPlaySubjectItem(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            subject = uiState.selectedQuestion.title,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = stringResource(R.string.music_list_count, uiState.recordListTotalCount), modifier = Modifier.padding(start = 16.dp), style = DPlayTheme.typography.capMed12, color = DPlayTheme.colors.gray500)
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier =
                Modifier
                    .weight(1f)
                    .background(color = DPlayTheme.colors.gray100)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = questionPosts.itemCount,
                key = { index -> questionPosts[index]?.postId ?: index },
                contentType = { "record-item" },
            ) { index ->
                val item = questionPosts[index] ?: return@items
                DPlayMusicListItem(
                    musicImageUrl = item.track.coverImg,
                    musicName = item.track.songTitle,
                    musicArtistName = item.track.artistName,
                    musicContent = item.content,
                    isEditorPick = (item.badge == BADGE.EDITOR),
                    onClick = { onMusicClick(item.postId) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecordListScreenPreview() {
    DPlayTheme {
        RecordListScreen(
            onBackButtonClick = {},
            onMusicClick = {},
        )
    }
}
