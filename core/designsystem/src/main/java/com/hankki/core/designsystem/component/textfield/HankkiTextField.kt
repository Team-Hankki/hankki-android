package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun HankkiTextField(
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    borderColor: Color,
    textColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
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
            }
        ),
        textStyle = HankkiTheme.typography.body1.copy(color = textColor),
        decorationBox = { innerTextField ->
            Row {
                Box {
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

@Preview(showBackground = true)
@Composable
fun HankkiTextFieldPreview() {
    HankkijogboTheme {
        Column {
//            HankkiTextField(
//                value = "",
//                placeholder = "예) 된장찌개",
//                borderColor = Gray300,
//                textColor = Gray300,
//                onTextChanged = {
//
//                }
//            )
//
//            HankkiTextField(
//                value = "김치찌개",
//                placeholder = "예) 된장찌개",
//                borderColor = Gray850,
//                textColor = Gray800,
//                onTextChanged = {
//
//                }
//            )
//
//            HankkiTextField(
//                value = "8000",
//                placeholder = "5000",
//                borderColor = Gray850,
//                textColor = Gray800,
//                onTextChanged = {
//
//                }
//            ) {
//                Text(
//                    text = "원",
//                    style = HankkiTheme.typography.body1,
//                    color = Gray800
//                )
//            }
        }
    }
}
