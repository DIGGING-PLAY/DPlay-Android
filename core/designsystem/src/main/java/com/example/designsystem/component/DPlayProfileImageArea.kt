package com.example.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayProfileImageArea(
    onProfileImageClick: () -> Unit,
    profileImagePath: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .noRippleClickable(
                    onClick = { onProfileImageClick() },
                ),
        contentAlignment = Alignment.BottomEnd
    ) {
        AsyncImage(
            model = profileImagePath ?: R.drawable.base_profile_image,
            contentDescription = null,
            modifier =
                Modifier
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = DPlayTheme.colors.gray200,
                        shape = CircleShape,
                    ),
            contentScale = ContentScale.Crop,
        )


        content()
    }
}
