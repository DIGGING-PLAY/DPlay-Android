package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayBookmarkButton(
    isMarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconRes = if (isMarked) R.drawable.ic_bookmark_filled_24 else R.drawable.ic_bookmark_unfilled_24
    val iconContentDescription =
        stringResource(
            if (isMarked) R.string.bookmark_button_filled_bookmark_icon_description else R.string.bookmark_button_unfilled_bookmark_icon_description,
        )

    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(10.dp),
        containerColor = DPlayTheme.colors.gray600,
        borderColor = DPlayTheme.colors.gray600,
    ) {
        DplayBaseIcon(
            iconRes = iconRes,
            contentDescription = iconContentDescription,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayBookmarkButtonPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayBookmarkButton(
                isMarked = true,
                onClick = {},
            )
            DPlayBookmarkButton(
                isMarked = false,
                onClick = {},
            )
        }
    }
}
