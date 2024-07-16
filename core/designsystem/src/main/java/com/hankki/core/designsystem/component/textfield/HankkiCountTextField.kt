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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.utill.KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER18_REGEX
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

    val borderColor = when {
        isFocused -> Gray400
        else -> Gray300
    }

    Column(modifier = modifier.background(White))
    {
        Text(
            text = title,
            style = HankkiTheme.typography.suitSub1,
            color = Gray900
        )

        Spacer(modifier = Modifier.height(6.dp))

        HankkiCountInnerTextField(
            value = value,
            placeholder = placeholder,
            borderColor = borderColor,
            textColor = Gray800,
            onFocusChanged = { isFocused = it },
            onTextChanged = { value ->
                if (KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER18_REGEX.matcher(value).matches()) {
                    if (tailingIcon) {
                        onValueChanged(value)
                    } else {
                        // 공백을 #으로 변환
                        var modifiedValue = value.replace(" ", "#")

                        // #이 연속으로 두 개 이상 있는지 확인
                        if (modifiedValue.count { it == '#' } > 2) {
                            // 공백 입력을 무시
                            return@HankkiCountInnerTextField
                        }

                        // 기존 연속된 #이 없는지 검사 및 수정
                        val parts = modifiedValue.split("#")
                        val filteredParts = parts.filter { it.isNotEmpty() }
                        val limitedParts = filteredParts.take(2)
                        modifiedValue = limitedParts.joinToString("#", prefix = "#")

                        // 공백 입력 시 처리
                        if (value.isNotEmpty() && value.last() == ' ') {
                            modifiedValue += "#"
                        }

                        onValueChanged(modifiedValue)

//                        1.#도 같이 글자수를 셈
//                        2.맨끝에서 공백 누르면 추가되지않음 => 해결함
//                        3.포인터가 이상 -> #ㅇ -> #ㄴㅏㅇ 이런식으로 한 글자!!만 추가되고 다음부터는 괜찮아짐

                    }
                }
            },
            tailingIcon = {
                //if (tailingIcon)
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
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
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
                    if (value.isEmpty()) {
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
