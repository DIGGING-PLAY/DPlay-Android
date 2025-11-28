package com.example.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.DplayBaseIcon
import com.example.designsystem.component.chip.type.DPlayChipType
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayChip(
    type: DPlayChipType,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .clip(
                    shape = RoundedCornerShape(16.dp),
                ).border(
                    width = 1.dp,
                    color = DPlayTheme.colors.dplayPink,
                    shape = RoundedCornerShape(16.dp),
                ).background(DPlayTheme.colors.dplayWhite)
                .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DplayBaseIcon(
            iconRes = type.iconRes,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(type.stringRes),
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.dplayPink,
        )
    }
}

@Preview
@Composable
fun DPlayChipPreview() {
    DPlayTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DPlayChip(type = DPlayChipType.NEW)
            DPlayChip(type = DPlayChipType.EDITOR)
            DPlayChip(type = DPlayChipType.BEST)
        }
    }
}
