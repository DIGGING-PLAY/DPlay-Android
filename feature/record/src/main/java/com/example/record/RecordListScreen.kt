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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.DPlayMusicListItem
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayLeftIconTitleTopAppBar
import com.example.designsystem.theme.DPlayTheme

@Composable
fun RecordListScreen(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: RecordContract.RecordState = RecordContract.RecordState(),
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

        Text(text = "총 ${uiState.recordList.size}개의 곡", modifier = Modifier.padding(start = 16.dp), style = DPlayTheme.typography.capMed12, color = DPlayTheme.colors.gray500)
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
                count = uiState.recordList.size,
                key = { index -> uiState.recordList[index].postId },
                contentType = { "record-item" },
            ) { index ->
                val item = uiState.recordList[index]
                DPlayMusicListItem(
                    musicImageUrl = item.track.coverImg,
                    musicName = item.track.songTitle,
                    musicArtistName = item.track.artistName,
                    musicContent = item.content,
                    onMoreClick = {},
                    isEditorPick = item.badges.isEditorPick,
                    onClick = {},
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
        )
    }
}
