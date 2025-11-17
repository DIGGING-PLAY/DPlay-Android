package com.example.designsystem.component.button

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.button.constant.DPlayToggleDefaults
import com.example.designsystem.component.button.constant.SwitchColors
import com.example.designsystem.component.button.constant.SwitchSizes
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayToggle(
    onClick: () -> Unit,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    sizes: SwitchSizes = DPlayToggleDefaults.sizes(),
    colors: SwitchColors = DPlayToggleDefaults.colors(),
) {
    val transition = updateTransition(isChecked)

    val thumbOffset by transition.animateDp { isChecked ->
        if (isChecked) sizes.thumbSize else 0.dp
    }

    val containerColor by transition.animateColor { isChecked ->
        if (isChecked) colors.checkedContainer else colors.uncheckedContainer
    }

    Box(
        modifier =
            modifier
                .width(sizes.containerWidth)
                .height(sizes.containerHeight)
                .background(
                    color = containerColor,
                    shape = RoundedCornerShape(sizes.cornerRadius),
                ).noRippleClickable(
                    onClick = onClick,
                    role = Role.Switch,
                ).padding(sizes.padding),
    ) {
        Thumb(
            thumbOffset = thumbOffset,
            thumbSize = sizes.thumbSize,
            thumbColor = colors.thumb,
        )
    }
}

@Composable
private fun Thumb(
    thumbOffset: Dp,
    thumbSize: Dp,
    thumbColor: Color,
) {
    Box(
        modifier =
            Modifier
                .size(thumbSize)
                .offset { IntOffset(x = thumbOffset.roundToPx(), y = 0) }
                .background(
                    color = thumbColor,
                    shape = CircleShape,
                ),
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    var isChecked by remember { mutableStateOf(false) }

    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayToggle(
                onClick = { isChecked = !isChecked },
                isChecked = isChecked,
            )
        }
    }
}
