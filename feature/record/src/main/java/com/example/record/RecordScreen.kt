package com.example.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.component.DplayTitleButtonTopAppBar
import com.example.designsystem.component.DplayTopAppBar
import com.example.designsystem.theme.DPlayTheme

@Composable
fun RecordScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        DplayTitleButtonTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "2025년 9월",
            onLeftClick = {},
            onButtonClick = {}
        )
        LazyColumn {
            items()
        }
    }
}

@Preview
@Composable
private fun RecordScreenPreview() {
    DPlayTheme{
        RecordScreen()
    }
}
