package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.WarnRed
import com.hankki.feature.storedetail.R

@Composable
private fun HankkiBaseField(
    modifier: Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    clearText: () -> Unit,
    isFocused: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    placeholder: String = "",
    suffix: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textColor: Color = Gray850,
    borderColor: Color = Gray500,
    content: (@Composable BoxScope.() -> Unit)? = null,
    focusManager: FocusManager
) {
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    LaunchedEffect(value) {
        if (textFieldValue.text != value) {
            textFieldValue = TextFieldValue(text = value, selection = TextRange(value.length))
        }
    }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            focusRequester.requestFocus()
            textFieldValue = textFieldValue.copy(selection = TextRange(value.length))
        }
    }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    onValueChange(newValue.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.dp,
                        when {
                            isError -> WarnRed
                            isEditing -> borderColor
                            else -> Color.Transparent
                        },
                        RoundedCornerShape(8.dp)
                    )
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 14.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isEditing = focusState.isFocused
                        onFocusChanged(focusState.isFocused)
                    },
                textStyle = HankkiTheme.typography.body2.copy(
                    color = if (isError) WarnRed else textColor,
                    textAlign = TextAlign.End
                ),
                singleLine = true,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            style = if (isEditing) {
                                HankkiTheme.typography.body4.copy(color = Gray850)
                            } else {
                                HankkiTheme.typography.body5.copy(color = Gray500)
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            if (value.isEmpty() && isEditing && placeholder.isNotEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Gray400,
                                    style = HankkiTheme.typography.body2,
                                    textAlign = TextAlign.End
                                )
                            }
                            content?.invoke(this) ?: innerTextField()
                        }

                        if (suffix.isNotEmpty()) {
                            Text(
                                text = suffix,
                                style = HankkiTheme.typography.body2.copy(color = Gray850)
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))
                        if (isEditing) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                                contentDescription = "Clear text",
                                modifier = Modifier
                                    .size(24.dp)
                                    .noRippleClickable(clearText),
                                tint = Color.Unspecified
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_bg_edit),
                                contentDescription = "Edit text",
                                modifier = Modifier
                                    .size(24.dp)
                                    .noRippleClickable {
                                        focusRequester.requestFocus()
                                        isEditing = true
                                    },
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            )
        }

        if (isError) {
            Text(
                text = errorMessage,
                color = WarnRed,
                style = HankkiTheme.typography.caption1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 16.dp),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun HankkiModMenuField(
    modifier: Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    clearText: () -> Unit,
    placeholder: String = "새로운 메뉴 이름",
    isFocused: Boolean,
    onMenuFocused: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current

    HankkiBaseField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        clearText = clearText,
        placeholder = placeholder,
        isFocused = isFocused,
        onFocusChanged = onMenuFocused,
        focusManager = focusManager
    )
}

@Composable
fun HankkiModPriceField(
    modifier: Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    clearText: () -> Unit,
    isError: Boolean = false,
    isFocused: Boolean,
    errorMessage: String = "유효하지 않은 가격입니다.",
    onPriceFocused: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current

    HankkiBaseField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        clearText = clearText,
        isFocused = isFocused,
        onFocusChanged = onPriceFocused,
        isError = isError,
        errorMessage = errorMessage,
        suffix = "원",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        focusManager = focusManager
    )
}
