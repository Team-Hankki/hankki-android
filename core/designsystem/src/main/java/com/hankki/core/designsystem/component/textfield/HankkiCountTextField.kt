package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.utill.KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

private const val TEXT_FIELD_LIMIT = 16

@Composable
fun HankkiCountTextField(
    title: String,
    value: String,
    valueLength: Int,
    placeholder: String,
    trailingIcon: Boolean,
    onValueChanged: (String) -> Unit,
    resetTitle: Boolean,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    val underLineColor = when {
        isFocused -> Gray500
        else -> Gray200
    }

    LaunchedEffect(resetTitle) {
        if (trailingIcon) {
            textFieldValue = TextFieldValue(text = "")
        }
    }

    Column(modifier = modifier.background(White)) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            style = HankkiTheme.typography.body6,
            color = Gray500
        )

        Spacer(modifier = Modifier.height(8.dp))

        HankkiCountInnerTextField(
            value = textFieldValue,
            placeholder = placeholder,
            underLineColor = underLineColor,
            textColor = Gray900,
            onFocusChanged = { focusState ->
                isFocused = focusState
                if (!trailingIcon) {
                    if (focusState) {
                        if (textFieldValue.text.isEmpty()) {
                            textFieldValue = TextFieldValue("#", selection = TextRange(1))
                            onValueChanged("#")
                        }
                    } else {
                        if (textFieldValue.text == "#") {
                            textFieldValue = TextFieldValue("")
                            onValueChanged("")
                        }
                    }
                }
            },
            onTextChanged = { newValue ->
                if (KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX.matcher(newValue.text)
                        .matches()
                ) {
                    if (trailingIcon) {
                        textFieldValue = newValue.copy(
                            text = newValue.text.take(TEXT_FIELD_LIMIT)
                        )
                        onValueChanged(textFieldValue.text)
                    } else {
                        var modifiedValue = newValue.text.replace(" ", "#")
                        val cursorPosition = if (modifiedValue.isEmpty()) {
                            1
                        } else {
                            newValue.selection.start.coerceAtLeast(1)
                                .coerceAtMost(modifiedValue.length)
                        }

                        if (newValue.text.contains("# ") || newValue.text.contains(" #")) {
                            return@HankkiCountInnerTextField
                        }

                        val parts = modifiedValue.split("#").filter { it.isNotEmpty() }
                        val limitedParts = parts.take(2)
                        val processedParts = limitedParts.map { it.take(8) }

                        modifiedValue = processedParts.joinToString("#", prefix = "#")

                        if (newValue.text.isNotEmpty() && newValue.text.last() == ' ' && modifiedValue.count { it == '#' } < 2) {
                            modifiedValue += "#"
                        }

                        textFieldValue = newValue.copy(
                            text = modifiedValue,
                            selection = TextRange(
                                start = cursorPosition,
                                end = cursorPosition
                            )
                        )

                        onValueChanged(modifiedValue)
                    }
                }
            },
            tailingIcon = {
                if (trailingIcon)
                    Text(
                        text = "(${valueLength}/$TEXT_FIELD_LIMIT)",
                        style = HankkiTheme.typography.body8,
                        color = if (isFocused) Gray500 else Gray300
                    )
            }
        )
    }
}

@Composable
fun HankkiCountInnerTextField(
    value: TextFieldValue,
    placeholder: String,
    onTextChanged: (TextFieldValue) -> Unit,
    underLineColor: Color,
    textColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    tailingIcon: @Composable () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = onTextChanged,
        modifier = modifier
            .background(backgroundColor)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                if (!focusManager.moveFocus(FocusDirection.Down)) {
                    focusManager.clearFocus()
                }
            },
            onSearch = {
                if (!focusManager.moveFocus(FocusDirection.Down)) {
                    focusManager.clearFocus()
                }
            }
        ),
        textStyle = HankkiTheme.typography.sub2.copy(color = textColor),
        decorationBox = { innerTextField ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)) {
                        innerTextField()
                        if (value.text.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Gray300,
                                style = HankkiTheme.typography.sub2
                            )
                        }
                    }
                    tailingIcon()
                }
                Spacer(Modifier.height(6.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = underLineColor
                )
            }

        }
    )
}

@Composable
@Preview
fun HankkiCountTextFieldPreview() {
    HankkijogboTheme {
        Column {
            HankkiCountTextField(
                title = "족보 제목",
                value = "",
                valueLength = 0,
                placeholder = "한식 맛집 모음",
                onValueChanged = {},
                trailingIcon = false,
                resetTitle = false
            )

            HankkiCountTextField(
                title = "족보 제목",
                value = "",
                valueLength = 0,
                placeholder = "한식 맛집 모음",
                onValueChanged = {},
                trailingIcon = true,
                resetTitle = false
            )
        }
    }
}
