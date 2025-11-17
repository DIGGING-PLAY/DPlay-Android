package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme

@Composable
fun DPlayCheckArrow(
    text: String,
    isChecked: Boolean,
    onCheckBoxClick: () -> Unit,
    onArrowClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val checkBoxIconRes = if (isChecked) R.drawable.ic_check_circle_pink_24 else R.drawable.ic_check_circle_lightgray_24

    Row(
        modifier = modifier
            .padding(
                horizontal = 12.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DplayClickableIcon(
            iconRes = checkBoxIconRes,
            onClick = onCheckBoxClick,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = DPlayTheme.typography.bodySemi16,
            color = DPlayTheme.colors.dplayBlack
        )

        Spacer(modifier = Modifier.weight(1f))

        DplayClickableIcon(
            iconRes = R.drawable.ic_arrow_right_16,
            onClick = onArrowClick,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DPlayCheckArrowPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            DPlayCheckArrow(
                text = stringResource(id = R.string.terms_service_required),
                isChecked = true,
                onArrowClick = {},
                onCheckBoxClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            DPlayCheckArrow(
                text = stringResource(id = R.string.privacy_policy_required),
                isChecked = false,
                onArrowClick = {},
                onCheckBoxClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}