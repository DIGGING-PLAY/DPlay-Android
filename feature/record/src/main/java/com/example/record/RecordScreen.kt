package com.example.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.DPlayDayTopicItem
import com.example.designsystem.component.DplayTitleButtonTopAppBar
import com.example.designsystem.theme.DPlayTheme
import com.example.navigation.Navigator

@Composable
fun RecordRoute(
    navigator: Navigator,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RecordSelectScreen(uiState = uiState)
}

@Composable
fun RecordSelectScreen(
    modifier: Modifier = Modifier,
    uiState: RecordContract.RecordState = RecordContract.RecordState(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        DplayTitleButtonTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "2025년 9월",
            onLeftClick = {},
            onButtonClick = {},
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding =
                PaddingValues(
                    vertical = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = uiState.questionList.size,
                key = { index -> uiState.questionList[index].questionId },
                contentType = { "question" },
            ) { index ->
                val item = uiState.questionList[index]

                DPlayDayTopicItem(
                    modifier = Modifier.fillMaxWidth(),
                    day = item.recordDay,
                    topic = item.title,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecordSelectScreenPreview() {
    DPlayTheme {
        RecordSelectScreen()
    }
}
