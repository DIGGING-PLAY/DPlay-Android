package com.example.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayErrorScreen(
    modifier: Modifier = Modifier,
    onBackIconClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DplayLeftIconTopAppBar {

        }

        Spacer(modifier = Modifier.height(height = 132.dp))

        Image(
            painter = painterResource(id = R.drawable.img_warning),
            contentDescription = null,
            modifier = Modifier.size(size = 140.dp)
        )

        Spacer(modifier = Modifier.height(height = 12.dp))

        Text(
            text = stringResource(R.string.error_main_text),
            style = DPlayTheme.typography.bodyBold16,
            color = DPlayTheme.colors.dplayBlack
        )

        Spacer(modifier = Modifier.height(height = 8.dp))

        Text(
            text = stringResource(R.string.error_sub_text),
            style = DPlayTheme.typography.bodyMed14,
            color = DPlayTheme.colors.gray400
        )
    }
}

@Preview
@Composable
private fun DPlayErrorScreenPreview(modifier: Modifier = Modifier) {
    DPlayTheme {
        DPlayErrorScreen(modifier = modifier)
    }
}