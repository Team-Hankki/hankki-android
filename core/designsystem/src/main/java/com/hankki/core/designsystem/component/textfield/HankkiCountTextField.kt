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
import androidx.compose.ui.unit.dp
import com.hankki.core.common.utill.KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiCountTextField(
    title: String,
    value: String,
    valueLength: Int,
    placeholder: String,
    tailingIcon: Boolean,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isFocused by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    val borderColor = when {
        isFocused -> Gray400
        else -> Gray300
    }

    Column(modifier = modifier.background(White)) {
        Text(
            text = title,
            style = HankkiTheme.typography.suitSub1,
            color = Gray900
        )

        Spacer(modifier = Modifier.height(6.dp))

        HankkiCountInnerTextField(
            value = textFieldValue,
            placeholder = placeholder,
            borderColor = borderColor,
            textColor = Gray800,
            onFocusChanged = { focusState ->
                isFocused = focusState
                if (!tailingIcon) {
                    if (focusState) {
                        if (textFieldValue.text.isEmpty()) {
                            textFieldValue = TextFieldValue(text = "#", selection = TextRange(1))
                            onValueChanged("#")
                        }
                    } else {
                        if (textFieldValue.text == "#") {
                            textFieldValue = TextFieldValue(text = "")
                            onValueChanged("")
                        }
                    }
                }
            },
            onTextChanged = { newValue ->
                if (KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER20_REGEX.matcher(newValue.text)
                        .matches()
                ) {
                    if (tailingIcon) {
                        textFieldValue = newValue.copy(
                            text = newValue.text.take(18),
                            selection = TextRange(newValue.text.length)
                        )
                        onValueChanged(textFieldValue.text)
                    } else {
                        var modifiedValue = newValue.text.replace(" ", "#") //필드 내 공백을 #으로 변환

                        if (newValue.text.contains("# ") || newValue.text.contains(" #")) { //공백과 #이 같이있다면 입력 막기
                            return@HankkiCountInnerTextField
                        }

                        val parts = modifiedValue.split("#").filter { it.isNotEmpty() }
                        val limitedParts = parts.take(2)
                        val processedParts = limitedParts.map { it.take(9) } //#을 기준으로 문자열 분리 후 각 태그에 대한 길이 제한

                        modifiedValue = processedParts.joinToString("#", prefix = "#") //각 태그 시작에 # 추가

                        if (newValue.text.isNotEmpty() && newValue.text.last() == ' ' && modifiedValue.count { it == '#' } < 2) { //사용자 공백 입력시 # 추가
                            modifiedValue += "#"
                        }

                        textFieldValue = newValue.copy(
                            text = modifiedValue,
                            selection = TextRange(modifiedValue.length)
                        )

                        onValueChanged(modifiedValue)
                    }
                }
            },
            tailingIcon = {
                if (tailingIcon)
                    Text(
                        text = "(${valueLength}/18)",
                        style = HankkiTheme.typography.body3,
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
    textColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
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
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
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
            tailingIcon = false
        )
    }
}
