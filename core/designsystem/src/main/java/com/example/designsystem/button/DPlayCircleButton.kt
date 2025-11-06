package com.example.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.button.type.CircleButtonType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayCircleButton(
    circleButtonType: CircleButtonType,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    Box(
        modifier = modifier
            .size(circleButtonType.containerSize)
            .noRippleClickable(onClick = onClick)
            .background(
                color = circleButtonType.backgroundColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ){
        Icon(
            imageVector = ImageVector.vectorResource(id = circleButtonType.iconRes),
            contentDescription = stringResource(circleButtonType.contentDescription),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayCircleButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            DPlayCircleButton(
                circleButtonType = CircleButtonType.SmallPlus()
            )
            DPlayCircleButton(
                circleButtonType = CircleButtonType.SmallClose()
            )
            DPlayCircleButton(
                circleButtonType = CircleButtonType.SmallEdit()
            )
            DPlayCircleButton(
                circleButtonType = CircleButtonType.LargePlus()
            )
        }
    }
}
