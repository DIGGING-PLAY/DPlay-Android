package com.example.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable

@Composable
fun DPlayUnderlineTextButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        modifier = modifier.noRippleClickable(
            onClick = onClick
        ),
        style = DPlayTheme.typography.capMed12.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = DPlayTheme.colors.gray400
    )
}

@Preview(showBackground = true)
@Composable
fun TextButtonPreview(){
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            DPlayUnderlineTextButton(
                onClick = {},
                text = "더 알아보기"
            )
            DPlayUnderlineTextButton(
                onClick = {},
                text = "기본 이미지로 변경하기"
            )
            DPlayUnderlineTextButton(
                onClick = {},
                text = "취소 하기"
            )
        }
    }
}
