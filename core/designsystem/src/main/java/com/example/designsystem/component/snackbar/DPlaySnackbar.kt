package com.example.designsystem.component.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import kotlinx.coroutines.delay

private const val SNACKBAR_DISPLAY_DURATION_MILLS = 2000L
private const val SNACKBAR_ANIMATION_DURATION_MILLS = 200

@Composable
fun DPlaySnackBar(
    type: SnackBarType,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onActionClick: (() -> Unit)? = null,
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(SNACKBAR_DISPLAY_DURATION_MILLS)
        isVisible = false
        delay(SNACKBAR_ANIMATION_DURATION_MILLS.toLong())
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter =
            slideInVertically(
                initialOffsetY = { it * 2 },
                animationSpec = tween(durationMillis = SNACKBAR_ANIMATION_DURATION_MILLS),
            ),
        exit =
            slideOutVertically(
                targetOffsetY = { it * 2 },
                animationSpec = tween(durationMillis = SNACKBAR_ANIMATION_DURATION_MILLS),
            ),
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(color = DPlayTheme.colors.gray500)
                    .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(type.iconRes),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(type.titleRes),
                color = DPlayTheme.colors.dplayWhite,
                style = DPlayTheme.typography.bodyMed14,
                modifier = Modifier.weight(1f),
            )

            type.actionStringRes?.let { actionRes ->
                Text(
                    text = stringResource(actionRes),
                    color = DPlayTheme.colors.dplayPink,
                    style = DPlayTheme.typography.bodyBold14,
                    modifier = Modifier.noRippleClickable { onActionClick?.invoke() },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DPlaySnackBarAddPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            DPlaySnackBar(
                type = SnackBarType.ADD,
                onActionClick = {},
                onDismiss = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DPlaySnackBarStreamingNotSupportPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
        ) {
            DPlaySnackBar(
                type = SnackBarType.STREAMING_NOT_SUPPORT,
                onActionClick = null,
                onDismiss = {},
            )
        }
    }
}
