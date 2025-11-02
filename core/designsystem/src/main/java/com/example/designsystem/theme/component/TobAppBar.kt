package com.example.designsystem.theme.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.icon.DplayBaseIcon
import com.example.designsystem.theme.icon.DplayClickableIcon
import com.example.designsystem.theme.util.noRippleClickable

@Composable
fun DplayTopAppBar(
    modifier: Modifier = Modifier,
    @DrawableRes leftIconRes: Int? = null,
    @DrawableRes rightIconRes: Int? = null,
    title: String? = null,
    onLeftClick: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { title?.let { Text(text = it) } },
        navigationIcon = {
            leftIconRes?.let {
                if (onLeftClick != null)
                    DplayClickableIcon(iconRes = it, onClick = onLeftClick)
                else
                    DplayBaseIcon(iconRes = it)
            }
        },
        actions = {
            rightIconRes?.let {
                if (onRightClick != null)
                    DplayClickableIcon(iconRes = it, onClick = onRightClick)
                else
                    DplayBaseIcon(iconRes = it)
            }
        }
    )
}


@Composable
fun DplayLogoTopAppBar(
    modifier: Modifier = Modifier,
    onListClick: (() -> Unit)? = null
) {
    DplayTopAppBar(
        modifier = modifier,
        leftIconRes = R.drawable.ic_wordmark_pink,
        rightIconRes = R.drawable.ic_list_24,
        onRightClick = onListClick
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
                Text(text = title)
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
    androidx.compose.foundation.layout.Column {
        DplayLogoTopAppBar(onListClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayLeftIconTopAppBar(onLeftClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayDualIconTopAppBar(onLeftClick = {}, onRightClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayRightIconTitleTopAppBar(title = "설정", onRightClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayLeftIconTitleTopAppBar(title = "뒤로가기", onLeftClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayDualIconTitleTopAppBar(title = "탑바 예시", onLeftClick = {}, onRightClick = {})
        Spacer(modifier = Modifier.height(8.dp))

        DplayTitleButtonTopAppBar(title = "정렬", onLeftClick = {}, onButtonClick = {})
    }
}