package com.example.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayBookmarkButton(
    modifier: Modifier = Modifier,
    isMarked: Boolean = false,
    onClick: () -> Unit = {}
){
    val iconRes = if (isMarked) R.drawable.ic_bookmark_filled_24 else R.drawable.ic_bookmark_unfilled_24
    val iconContentDescription = stringResource(
        if (isMarked) R.string.button_filled_bookmark_icon_description else R.string.button_unfilled_bookmark_icon_description
    )

    Box(
        modifier = modifier
            .size(44.dp)
            .noRippleClickable(
                onClick = onClick,
                role = Role.Button
            )
            .background(
                color = DPlayTheme.colors.gray600,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = iconContentDescription,
            tint = DPlayTheme.colors.dplayWhite
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayBookmarkButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            DPlayBookmarkButton()
            DPlayBookmarkButton(isMarked = true)
        }
    }
}