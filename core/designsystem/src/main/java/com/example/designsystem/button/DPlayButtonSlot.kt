package com.example.designsystem.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayButtonSlot(
    paddingValues: PaddingValues,
    containerColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
){
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(
                    color = containerColor
                )
                .noRippleClickable(
                    enabled = enabled,
                    onClick = onClick,
                    role = Role.Button
                ).padding(paddingValues),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.wrapContentSize(),
        ) {
            content()
        }
    }
}