package com.example.designsystem.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayScrim(
    backgroundColor: Color,
    onDismiss: () -> Unit,
){
    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(color = backgroundColor)
        .noRippleClickable { onDismiss() },
    ){
        BackHandler { onDismiss() }
    }
}