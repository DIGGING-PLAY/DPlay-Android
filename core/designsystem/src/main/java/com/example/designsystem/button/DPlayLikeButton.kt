package com.example.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayLikeButton(
    likeCount: Int = 0,
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onClick: () -> Unit = {}
){
    val containerColor = if(isLiked) DPlayTheme.colors.dplayPink100 else DPlayTheme.colors.dplayWhite
    val iconRes = if(isLiked) R.drawable.ic_heart_pink_filled_24 else R.drawable.ic_heart_pink_unfilled_24
    val stringRes = if(isLiked) R.string.like_button_filled_heart_description else R.string.like_button_unfilled_heart_description

    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(vertical = 12.dp),
        containerColor = containerColor,
        borderColor = DPlayTheme.colors.dplayPink,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(iconRes),
                    contentDescription = stringResource(stringRes),
                    tint = Color.Unspecified
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text(
                    text = likeCount.toString(),
                    style = DPlayTheme.typography.bodySemi14,
                    color = DPlayTheme.colors.dplayPink
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DPlayLikeButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Row(modifier = Modifier.fillMaxWidth()){
                DPlayLikeButton(
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.size(8.dp))

                DPlayLikeButton(
                    modifier = Modifier.weight(1f),
                    isLiked = true
                )
            }
        }
    }
}