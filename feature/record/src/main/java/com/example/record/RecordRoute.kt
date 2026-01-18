package com.example.record

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.navigation.Detail
import com.example.navigation.Navigator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecordRoute(
    navigator: Navigator,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is RecordContract.RecordSideEffect.ShowSnackBar -> TODO()
                is RecordContract.RecordSideEffect.NavigateToPostDetail -> {
                    navigator.navigateTo(Detail(it.postId))
                }
            }
        }
    }

    when (uiState.selectedQuestion) {
        null ->
            RecordSelectScreen(
                uiState = uiState,
                onQuestionClick = { question ->
                    viewModel.handleIntent(RecordContract.RecordIntent.OnQuestionClick(question))
                },
                changeDatePickerBottomSheetVisible = { isVisible ->
                    viewModel.handleIntent(RecordContract.RecordIntent.ChangeBottomSheetVisible(isVisible = isVisible))
                },
                onDateSelectClick = { year, month ->
                    viewModel.handleIntent(RecordContract.RecordIntent.SelectDate(year = year, month = month))
                },
            )

        else ->
            RecordListScreen(
                uiState = uiState,
                onBackButtonClick = { viewModel.handleIntent(RecordContract.RecordIntent.OnListBackButtonClick) },
                onMusicClick = { postId ->
                    viewModel.handleIntent(RecordContract.RecordIntent.OnMusicClick(postId = postId))
                },
            )
    }
}
