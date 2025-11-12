package com.example.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.textfield.constant.TextFieldConstant
import com.example.designsystem.theme.DPlayTheme

private const val TEXT_AREA_ASPECT_RATIO = 343f / 180f

@Composable
fun DPlayTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLength: Int = TextFieldConstant.MAX_COMMENT_LENGTH,
    onEnterClick: () -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
){
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                onFocusChange(it.isFocused)
            }
            .background(
                color = DPlayTheme.colors.gray100,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 16.dp, horizontal = 12.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onEnterClick()
                focusManager.clearFocus(force = true)
            }
        ),
        textStyle = DPlayTheme.typography.bodySemi14.copy(color = DPlayTheme.colors.dplayBlack),
        cursorBrush = SolidColor(value = DPlayTheme.colors.dplayPink),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(TEXT_AREA_ASPECT_RATIO)
                ,
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = DPlayTheme.colors.gray400,
                            style = DPlayTheme.typography.bodySemi16
                        )
                    }

                    innerTextField()
                }

                Spacer(modifier = Modifier.size(4.dp))

                CharacterCounterText(
                    currentLength = value.length,
                    maxLength = maxLength,
                    modifier = Modifier.align(alignment = Alignment.End)
                )
            }
        }
    )
}

@Preview
@Composable
fun DPlayTextAreaPreview() {
    var text by remember { mutableStateOf("") }
    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DPlayTheme.colors.dplayWhite)
                .padding(16.dp)
        ) {
            DPlayTextArea(
                value = text,
                onValueChange = { text = it },
                placeholder = stringResource(id = R.string.placeholder_comment),
            )
        }
    }
}