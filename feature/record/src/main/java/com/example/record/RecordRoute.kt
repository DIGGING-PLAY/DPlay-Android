package com.example.record

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.navigation.Navigator

@Composable
fun RecordRoute(
    navigator: Navigator,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.selectedQuestion) {
        null ->
            RecordSelectScreen(uiState = uiState, onQuestionClick = { question ->
                viewModel.handleIntent(RecordContract.RecordIntent.OnQuestionClick(question))
            })
        else ->
            RecordListScreen(
                uiState = uiState,
                onBackButtonClick = { viewModel.handleIntent(RecordContract.RecordIntent.OnListBackButtonClick) },
            )
    }
}
