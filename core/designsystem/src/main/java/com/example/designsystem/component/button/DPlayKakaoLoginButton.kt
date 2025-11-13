package com.example.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.icon.DplayBaseIcon

@Composable
fun DPlayKakaoLoginButton(
    onClick: () -> Unit,
    modifier : Modifier = Modifier
){
    DPlayButtonSlot(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(20.dp),
        containerColor = DPlayTheme.colors.kakaoYellow,
        borderColor = DPlayTheme.colors.kakaoYellow,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            DplayBaseIcon(
                iconRes = R.drawable.ic_kakao_24,
            )

            Text(
                text = stringResource(R.string.kakao_login_button_label),
                style = DPlayTheme.typography.bodyBold16,
                color = DPlayTheme.colors.dplayBlack
            )

            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayKakaoLoginButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DPlayKakaoLoginButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}