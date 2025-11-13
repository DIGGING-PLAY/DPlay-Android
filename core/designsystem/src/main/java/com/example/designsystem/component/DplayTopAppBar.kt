package com.example.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DplayTopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIconRes: Int? = null,
    @DrawableRes rightIconRes: Int? = null,
    title: String? = null,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
) {
    val iconPaddingModifier = Modifier.padding(12.dp)

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it,
                    style = DPlayTheme.typography.titleBold18,
                    color = DPlayTheme.colors.dplayBlack
                )
            }
        },
        navigationIcon = {
            leftIconRes?.let {
                if (onLeftClick != null)
                    DplayClickableIcon(
                        modifier = iconPaddingModifier,
                        iconRes = it,
                        onClick = onLeftClick
                    )
                else
                    DplayBaseIcon(modifier = iconPaddingModifier, iconRes = it)
            }
        },
        actions = {
            rightIconRes?.let {
                if (onRightClick != null)
                    DplayClickableIcon(
                        modifier = iconPaddingModifier,
                        iconRes = it,
                        onClick = onRightClick
                    )
                else
                    DplayBaseIcon(modifier = iconPaddingModifier, iconRes = it)
            }
        }
    )
}


@Composable
fun DplayLogoTopAppBar(
    modifier: Modifier = Modifier,
    onListClick: (() -> Unit)
) {
    val iconPaddingModifier = Modifier.padding(12.dp)

    CenterAlignedTopAppBar(
        modifier = modifier,
        navigationIcon = {
            DplayBaseIcon(
                modifier = iconPaddingModifier.padding(start = 4.dp),
                iconRes = R.drawable.ic_wordmark_pink
            )

        },
        actions = {
            DplayClickableIcon(
                modifier = iconPaddingModifier,
                iconRes = R.drawable.ic_list_24,
                onClick = onListClick
            )
        },
        title = {}
    )
}


@Composable
fun DplayLeftIconTopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIconRes: Int = R.drawable.ic_arrow_left_16,
    onLeftClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        leftIconRes = leftIconRes,
        onLeftClick = onLeftClick
    )
}

@Composable
fun DplayDualIconTopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIconRes: Int = R.drawable.ic_arrow_left_16,
    @DrawableRes rightIconRes: Int = R.drawable.ic_more_24,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        leftIconRes = leftIconRes,
        rightIconRes = rightIconRes,
        onLeftClick = onLeftClick,
        onRightClick = onRightClick
    )
}

@Composable
fun DplayRightIconTitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes rightIconRes: Int = R.drawable.ic_setting_24,
    onRightClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        title = title,
        rightIconRes = rightIconRes,
        onRightClick = onRightClick
    )
}

@Composable
fun DplayLeftIconTitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes leftIconRes: Int = R.drawable.ic_arrow_left_16,
    onLeftClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        title = title,
        leftIconRes = leftIconRes,
        onLeftClick = onLeftClick
    )
}

@Composable
fun DplayDualIconTitleTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes leftIconRes: Int = R.drawable.ic_arrow_left_16,
    @DrawableRes rightIconRes: Int = R.drawable.ic_more_24,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        title = title,
        leftIconRes = leftIconRes,
        rightIconRes = rightIconRes,
        onLeftClick = onLeftClick,
        onRightClick = onRightClick
    )
}

@Composable
fun DplayTitleButtonTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes leftIconRes: Int = R.drawable.ic_arrow_left_16,
    @DrawableRes buttonIconRes: Int = R.drawable.ic_arrow_down,
    onLeftClick: (() -> Unit)? = null,
    onButtonClick: (() -> Unit)? = null
) {
    val clickableModifier = Modifier.then(
        if (onButtonClick != null) Modifier.noRippleClickable(onClick = onButtonClick) else Modifier
    )

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Row(modifier = clickableModifier, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = DPlayTheme.typography.titleBold18,
                    color = DPlayTheme.colors.dplayBlack
                )
                Spacer(modifier = Modifier.width(4.dp))
                DplayBaseIcon(iconRes = buttonIconRes)
            }
        },
        navigationIcon = {
            if (onLeftClick != null)
                DplayClickableIcon(iconRes = leftIconRes, onClick = onLeftClick)
            else
                DplayBaseIcon(iconRes = leftIconRes)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDplayTopAppBars() {
    DPlayTheme {
        Column {
            DplayLogoTopAppBar(onListClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayLeftIconTopAppBar(onLeftClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayDualIconTopAppBar(onLeftClick = {}, onRightClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayRightIconTitleTopAppBar(title = "Title", onRightClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayLeftIconTitleTopAppBar(title = "Title", onLeftClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayDualIconTitleTopAppBar(title = "Title", onLeftClick = {}, onRightClick = {})
            Spacer(modifier = Modifier.height(36.dp))

            DplayTitleButtonTopAppBar(title = "Title", onLeftClick = {}, onButtonClick = {})
        }
    }
}