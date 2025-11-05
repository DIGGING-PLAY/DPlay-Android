package com.example.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dplay.designsystem.R
import com.example.designsystem.component.textfield.constant.TextFieldConstant
import com.example.designsystem.component.textfield.type.InputState
import com.example.designsystem.component.textfield.type.NicknameInputState
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.theme.defaultDPlayColors

@Composable
fun DPlayTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputState: InputState = InputState.Default,
    placeholder: String = "",
    maxLength: Int? = null,
    onEnterClick: () -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val borderModifier = remember(inputState) {
        when (inputState) {
            is InputState.Error -> Modifier.border(
                width = 1.dp,
                color = defaultDPlayColors.alertRed,
                shape = RoundedCornerShape(16.dp)
            )
            is InputState.Success -> Modifier.border(
                width = 1.dp,
                color = defaultDPlayColors.infoBlue,
                shape = RoundedCornerShape(16.dp)
            )
            else -> Modifier
        }
    }

    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (maxLength == null || it.length <= maxLength) onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                }
                .background(
                    color = DPlayTheme.colors.gray100,
                    shape = RoundedCornerShape(16.dp)
                )
                .then(borderModifier)
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
            singleLine = true,
            textStyle = DPlayTheme.typography.bodySemi16.copy(color = DPlayTheme.colors.dplayBlack),
            cursorBrush = SolidColor(value = DPlayTheme.colors.dplayPink),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
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

                    if (value.isNotEmpty()) {
                        Spacer(modifier = Modifier.size(8.dp))

                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_close_20),
                            contentDescription = stringResource(R.string.text_input_clear_description),
                            tint = DPlayTheme.colors.gray400,
                            modifier = Modifier
                                .background(
                                    color = DPlayTheme.colors.gray200,
                                    shape = CircleShape
                                )
                                .clickable(
                                    role = Role.Button,
                                ) { onValueChange("") }
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.size(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
                ) {
            when (inputState) {
                is InputState.Error -> {
                    Text(
                        text = inputState.getMessage(),
                        color = DPlayTheme.colors.alertRed,
                        style = DPlayTheme.typography.capMed12,
                    )
                }
                is InputState.Success -> {
                    Text(
                        text = inputState.getMessage(),
                        color = DPlayTheme.colors.infoBlue,
                        style = DPlayTheme.typography.capMed12,
                    )
                }
                is InputState.Default -> Unit
            }

            Spacer(modifier = Modifier.weight(1f))

            if (maxLength != null) {
                Text(
                    text = stringResource(R.string.text_input_character_counter, value.length, maxLength),
                    color = DPlayTheme.colors.gray400,
                    style = DPlayTheme.typography.capMed12,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DPlayTextInputPreview() {
    var nickname by remember { mutableStateOf("") }
    var music by remember { mutableStateOf("") }

    DPlayTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                inputState = NicknameInputState.Success,
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                inputState = NicknameInputState.Error.AlreadyExists,
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                inputState = NicknameInputState.Error.NotEnoughLength,
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                inputState = NicknameInputState.Error.InvalidFormat,
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = nickname,
                onValueChange = { nickname = it },
                onFocusChange = {},
                inputState = NicknameInputState.Error.ForbiddenWord,
                placeholder = stringResource(R.string.placeholder_nickname),
                maxLength = TextFieldConstant.MAX_NICKNAME_LENGTH
            )

            DPlayTextInput(
                value = music,
                onValueChange = { music = it },
                placeholder = stringResource(R.string.placeholder_music_search),
            )
        }
    }
}