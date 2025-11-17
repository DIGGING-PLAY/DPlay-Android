package com.example.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayImageCheck(
    imageUrl: String,
    musicName: String,
    artistName: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val backgroundColor = if (isChecked) DPlayTheme.colors.gray100 else Color.Transparent

    Row(
        modifier = modifier
            .background(color = backgroundColor)
            .noRippleClickable(
                onClick = onClick
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.image_check_image_description, musicName),
            modifier = Modifier.size(52.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = musicName,
                style = DPlayTheme.typography.bodySemi16,
                color = DPlayTheme.colors.dplayBlack
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = artistName,
                style = DPlayTheme.typography.bodyMed14,
                color = DPlayTheme.colors.gray400
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (isChecked) {
            DplayBaseIcon(
                iconRes = R.drawable.ic_check_circle_darkgray_24
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DPlayImageCheckPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            DPlayImageCheck(
                imageUrl = "",
                musicName = "내일에서 온 티켓",
                artistName = "한로로",
                isChecked = true,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            DPlayImageCheck(
                imageUrl = "",
                musicName = "내일에서 온 티켓",
                artistName = "한로로",
                isChecked = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}