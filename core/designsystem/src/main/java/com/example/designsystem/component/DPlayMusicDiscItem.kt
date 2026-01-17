package com.example.designsystem.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.designsystem.theme.DPlayTheme

private const val DISC_ROTATION_DURATION_MILLIS = 10000 // 10초

@Composable
fun DPlayMusicDiscItem(
    imageUrl: String,
    modifier: Modifier = Modifier,
    isStreaming: Boolean = false,
) {
    val grayBorderColor = DPlayTheme.colors.gray200
    val infiniteTransition = rememberInfiniteTransition(label = "disc_rotation")

    val animatedRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = DISC_ROTATION_DURATION_MILLIS, easing = LinearEasing),
            ),
        label = "rotation",
    )

    var startOffset by remember { mutableFloatStateOf(animatedRotation) }
    var wasStreaming by remember { mutableStateOf(false) }

    if (isStreaming && !wasStreaming) {
        startOffset = animatedRotation
    }
    wasStreaming = isStreaming

    val rotation =
        if (isStreaming) {
            (animatedRotation - startOffset + 360f) % 360f
        } else {
            0f
        }

    Box(
        modifier =
            modifier
                .aspectRatio(1f)
                .graphicsLayer {
                    rotationZ = rotation
                    compositingStrategy = CompositingStrategy.Offscreen
                }.clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = grayBorderColor,
                    shape = CircleShape,
                ).drawWithContent {
                    drawContent()

                    val cx = size.width / 2
                    val cy = size.height / 2
                    val holeRadius = size.minDimension * 0.0625f

                    drawCircle(
                        color = Color.Transparent,
                        radius = holeRadius,
                        center = Offset(cx, cy),
                        blendMode = BlendMode.Clear,
                    )

                    drawCircle(
                        color = grayBorderColor,
                        radius = holeRadius,
                        center = Offset(cx, cy),
                        style = Stroke(width = 2.dp.toPx()),
                    )
                },
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
