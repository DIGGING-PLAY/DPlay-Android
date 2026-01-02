package com.example.dummy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.Dummy
import com.example.domain.repository.DummyRepository
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DummyScreen(
    viewModel: DummyViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DummyContract.DummyIntent.Initialize)
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is DummyContract.DummySideEffect.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    DummyScreen(
        snackbarHostState = snackbarHostState,
        state = state,
        onNumberButtonClick = {
            viewModel.handleIntent(DummyContract.DummyIntent.OnClickNumberButton(it))
        },
    )
}

@Composable
private fun DummyScreen(
    snackbarHostState: SnackbarHostState,
    state: DummyContract.DummyState,
    onNumberButtonClick: (Int) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Count: ${state.count}")
            Button(
                onClick = { onNumberButtonClick(1) },
            ) {
                Text(text = "1")
            }

            Button(
                onClick = { onNumberButtonClick(2) },
            ) {
                Text(text = "2")
            }
        }
    }
}

@Preview
@Composable
private fun DummyScreenPreview() {
    DummyScreen()
}
