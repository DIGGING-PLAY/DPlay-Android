package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BottomNavigationBar(
    isVisible: Boolean,
    topLevelRouteList: ImmutableList<TopLevelRoute>,
    currentTab: Any?,
    onBottomNavigationItemClick: (TopLevelRoute) -> Unit,
    onPlusButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (isVisible) {
        Box(
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(color = DPlayTheme.colors.dplayWhite)
                        .border(
                            width = 1.dp,
                            color = DPlayTheme.colors.gray200,
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        ).padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                topLevelRouteList.forEach { tab ->
                    BottomNavigationItem(
                        isSelected = currentTab == tab,
                        tab = tab,
                        onBottomNavigationItemClick = { onBottomNavigationItemClick(it) },
                    )
                }
            }

            DPlayCircleButton(
                circleButtonType = CircleButtonType.LargePlus(),
                onClick = onPlusButtonClick,
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-28).dp),
            )
        }
    }
}

@Composable
private fun BottomNavigationItem(
    isSelected: Boolean,
    tab: TopLevelRoute,
    onBottomNavigationItemClick: (TopLevelRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .noRippleClickable(
                    onClick = { onBottomNavigationItemClick(tab) },
                ).padding(vertical = 12.dp, horizontal = 48.dp),
        contentAlignment = Alignment.Center,
    ) {
        DplayBaseIcon(
            iconRes = if (isSelected) tab.selectedIconRes else tab.unselectedIconRes,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    var currentTab by remember { mutableStateOf<TopLevelRoute>(Home) }
    DPlayTheme {
        BottomNavigationBar(
            isVisible = true,
            topLevelRouteList = persistentListOf(Home, MyPage),
            currentTab = currentTab,
            onBottomNavigationItemClick = {
                currentTab = it
            },
        )
    }
}
