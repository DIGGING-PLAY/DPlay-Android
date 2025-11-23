package com.example.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.roundedBackgroundWithPadding

@Composable
fun DPlaySubjectItem(
    subject: String,
    modifier: Modifier = Modifier,
) {
    val color = DPlayTheme.colors
    val typography = DPlayTheme.typography
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = color.gray200, shape = RoundedCornerShape(12.dp))
                .roundedBackgroundWithPadding(
                    backgroundColor = color.gray100,
                    cornerRadius = 12.dp,
                    padding = PaddingValues(12.dp),
                ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DplayBaseIcon(
                iconRes = R.drawable.ic_symbol_20,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(R.string.subect_title), style = typography.bodySemi14, color = color.dplayPink)
        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = subject, style = typography.bodySemi14, color = color.dplayBlack)
    }
}

@Preview
@Composable
private fun DPlaySubjectItemPreview() {
    DPlayTheme {
        DPlaySubjectItem(
            subject = "여행 갈 때 플레이리스트에 꼭 넣는 노래는?",
        )
    }
}
