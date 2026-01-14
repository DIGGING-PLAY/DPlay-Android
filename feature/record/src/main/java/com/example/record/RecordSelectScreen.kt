package com.example.record

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.model.DailyQuestion
import com.example.designsystem.component.DPlayDatePickerBottomSheet
import com.example.designsystem.component.DPlayDayTopicItem
import com.example.designsystem.component.DplayTitleButtonTopAppBar
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun RecordSelectScreen(
    onQuestionClick: (question: DailyQuestion) -> Unit,
    changeDatePickerBottomSheetVisible: (Boolean) -> Unit,
    onDateSelectClick: (year: Int, month: Int) -> Unit,
    modifier: Modifier = Modifier,
    uiState: RecordContract.RecordState = RecordContract.RecordState(),
) {
    if (uiState.datePickerBottomSheetVisible) {
        BackHandler {
            changeDatePickerBottomSheetVisible(false)
        }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            DplayTitleButtonTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "${uiState.year}년 ${uiState.month}월",
                onLeftClick = {},
                onButtonClick = { changeDatePickerBottomSheetVisible(true) },
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
                        onClick = { onQuestionClick(item) },
                    )
                }
            }
        }
        if (uiState.datePickerBottomSheetVisible) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = DPlayTheme.colors.dim40)
                        .noRippleClickable { changeDatePickerBottomSheetVisible(false) },
            )
            DPlayDatePickerBottomSheet(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .noRippleClickable(),
                onCloseClick = { changeDatePickerBottomSheetVisible(false) },
                onApplyClick = { year, month ->
                    onDateSelectClick(year, month)
                    changeDatePickerBottomSheetVisible(false)
                },
                initialYear = uiState.year,
                initialMonth = uiState.month,
            )
        }
    }
}

@Preview
@Composable
private fun RecordSelectScreenPreview() {
    DPlayTheme {
        RecordSelectScreen(
            onQuestionClick = {},
            changeDatePickerBottomSheetVisible = {},
            onDateSelectClick = { _, _ -> },
        )
    }
}
