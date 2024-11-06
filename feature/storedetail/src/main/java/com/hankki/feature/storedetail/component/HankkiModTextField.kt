package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
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
fun HankkiModMenuField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    clearText: () -> Unit,
    placeholder: String = "새로운 메뉴 이름",
    isFocused: Boolean,
    onMenuFocused: (Boolean) -> Unit
) {
    var isEditingMenu by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    if (isFocused) Gray500 else Transparent,
                    RoundedCornerShape(8.dp)
                )
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 14.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isEditingMenu = focusState.isFocused
                    onMenuFocused(focusState.isFocused)
                },
            textStyle = HankkiTheme.typography.body2.copy(
                color = Gray850,
                textAlign = TextAlign.End
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = label,
                        style = if (isEditingMenu) {
                            HankkiTheme.typography.body4.copy(color = Gray850)
                        } else {
                            HankkiTheme.typography.body5.copy(color = Gray500)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (value.text.isEmpty() && isEditingMenu) {
                            Text(
                                text = placeholder,
                                color = Gray400,
                                style = HankkiTheme.typography.body2,
                                textAlign = TextAlign.End
                            )
                        } else {
                            innerTextField()
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                    if (value.text.isNotEmpty() && isEditingMenu) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cancel),
                            contentDescription = "Clear text",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable(clearText),
                            tint = Color.Unspecified
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_bg_edit),
                            contentDescription = "Edit text",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable {
                                    onMenuFocused(true)
                                },
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun HankkiModPriceField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    clearText: () -> Unit,
    isError: Boolean = false,
    isFocused: Boolean,
    errorMessage: String = "유효하지 않은 가격입니다.",
    warnRed: Color = WarnRed,
    onPriceFocused: (Boolean) -> Unit
) {
    var isEditingPrice by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            focusRequester.requestFocus()
        }
    }

    val borderColor = when {
        isError -> warnRed
        isEditingPrice -> Gray500
        else -> Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    1.dp, borderColor, RoundedCornerShape(10.dp)
                )
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 14.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isEditingPrice = focusState.isFocused
                    onPriceFocused(focusState.isFocused)
                },
            textStyle = HankkiTheme.typography.body2.copy(
                color = if (isError) warnRed else Gray850,
                textAlign = TextAlign.End
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = if (isEditingPrice) {
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
                        innerTextField()
                    }

                    Text(
                        text = "원",
                        style = HankkiTheme.typography.body2.copy(color = Gray850)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))
                    if (isEditingPrice) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cancel),
                            contentDescription = "Clear text",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable(clearText),
                            tint = Color.Unspecified
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_bg_edit),
                            contentDescription = "Edit text",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable {
                                    focusRequester.requestFocus()
                                    isEditingPrice = true
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
            color = warnRed,
            style = HankkiTheme.typography.caption1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            textAlign = TextAlign.End
        )
    }
}