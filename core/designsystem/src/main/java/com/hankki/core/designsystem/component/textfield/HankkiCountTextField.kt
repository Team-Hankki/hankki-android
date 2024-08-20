package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hankki.core.common.utill.KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX
import com.hankki.core.designsystem.constant.FOCUSED_BORDER_RADIUS
import com.hankki.core.designsystem.constant.UNFOCUSED_BORDER_RADIUS
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
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
    resetTitle : Boolean,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    val borderColor = when {
        isFocused -> Gray600
        else -> Gray300
    }

    val borderWidth = when {
        isFocused -> FOCUSED_BORDER_RADIUS.dp
        else -> UNFOCUSED_BORDER_RADIUS.dp
    }

    LaunchedEffect(resetTitle) {
        if (trailingIcon) {
            textFieldValue = TextFieldValue(text = "")
        }
    }

    Column(modifier = modifier.background(White)) {
        Text(
            text = title,
            style = HankkiTheme.typography.suitSub2,
            color = Gray900
        )

        Spacer(modifier = Modifier.height(6.dp))

        HankkiCountInnerTextField(
            value = textFieldValue,
            placeholder = placeholder,
            borderColor = borderColor,
            borderWidth = borderWidth,
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
                            newValue.selection.start.coerceAtLeast(1).coerceAtMost(modifiedValue.length)
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
                        style = HankkiTheme.typography.body4,
                        color = Gray400
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
    borderColor: Color,
    borderWidth: Dp,
    textColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable () -> Unit = {},
    tailingIcon: @Composable () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value,
        onValueChange = onTextChanged,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(12.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
            onSearch = {
                focusManager.clearFocus()
            }
        ),
        textStyle = HankkiTheme.typography.body1.copy(color = textColor),
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                leadingIcon()
                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()
                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Gray400,
                            style = HankkiTheme.typography.body1
                        )
                    }
                }
                tailingIcon()
            }
        }
    )
}

@Composable
@Preview
fun HankkiCountTextFieldPreview() {
    HankkijogboTheme {
        HankkiCountTextField(
            title = "족보의 제목을 지어주세요",
            value = "",
            valueLength = 0,
            placeholder = "성대생 추천 맛집 드려요",
            onValueChanged = {},
            trailingIcon = false,
            resetTitle = false
        )
    }
}
