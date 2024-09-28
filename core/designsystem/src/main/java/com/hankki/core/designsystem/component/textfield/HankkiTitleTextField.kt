package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.utill.KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER30_REGEX
import com.hankki.core.common.utill.VALID_NUMBER_UNDER_100000_REGEX
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.constant.FOCUSED_BORDER_RADIUS
import com.hankki.core.designsystem.constant.UNFOCUSED_BORDER_RADIUS
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500

@Composable
fun HankkiTitleTextField(
    title: String,
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    errorMessage: String = stringResource(id = R.string.enable_under_eight_thousand_won),
    tailingIcon: @Composable () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }

    val titleColor = when {
        isError -> Red500
        isFocused -> Gray800
        else -> Gray500
    }

    val borderColor = when {
        isError -> Red500
        isFocused -> Gray850
        else -> Gray300
    }

    val textColor = when {
        isError -> Red500
        else -> Gray800
    }

    Column {
        Row {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = HankkiTheme.typography.body8,
                color = titleColor
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        HankkiTextField(
            value = value,
            placeholder = placeholder,
            onTextChanged = onTextChanged,
            border = if (isFocused) FOCUSED_BORDER_RADIUS.dp else UNFOCUSED_BORDER_RADIUS.dp,
            borderColor = borderColor,
            textColor = textColor,
            tailingIcon = tailingIcon,
            modifier = modifier,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            onFocusChanged = { isFocused = it }
        )

        if (isError) {
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = errorMessage,
                    style = HankkiTheme.typography.caption1,
                    color = Red500
                )
            }
        }
    }
}

@Composable
fun HankkiMenuTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "예) 된장찌개",
    title: String = "메뉴 이름",
) {
    HankkiTitleTextField(
        title = title,
        value = value,
        placeholder = placeholder,
        onTextChanged = { menu ->
            if (KOREAN_NUMBER_ENGLISH_SPECIAL_SPACE_UNDER30_REGEX.matcher(menu).matches()) {
                onTextChanged(menu)
            }
        },
        modifier = modifier,
    )
}

@Composable
fun HankkiPriceTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier,
    title: String = "가격",
    placeholder: String = "8000",
) {
    HankkiTitleTextField(
        title = title,
        value = value,
        placeholder = placeholder,
        onTextChanged = { price ->
            if (VALID_NUMBER_UNDER_100000_REGEX.matcher(price).matches()) {
                onTextChanged(price)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        isError = isError,
        modifier = modifier
    ) {
        Text(
            text = "원",
            style = HankkiTheme.typography.body1,
            color = Gray800
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HankkiTitleTextFieldPreview() {
    HankkijogboTheme {
        Column {
            HankkiMenuTextField(
                title = "메뉴 이름",
                value = "",
                placeholder = "예) 된장찌개",
                onTextChanged = {}
            )
            HankkiMenuTextField(
                title = "메뉴 이름",
                value = "김치찌개",
                placeholder = "예) 된장찌개",
                onTextChanged = {}
            )
            HankkiMenuTextField(
                title = "메뉴 이름",
                value = "김치찌개",
                placeholder = "예) 된장찌개",
                onTextChanged = {}
            )

            HankkiPriceTextField(
                title = "가격",
                value = "",
                placeholder = "7900",
                onTextChanged = {},
                isError = false,
            )

            HankkiPriceTextField(
                title = "가격",
                value = "8000",
                placeholder = "7900",
                onTextChanged = {},
                isError = false,
            )

            HankkiPriceTextField(
                title = "가격",
                value = "9000",
                placeholder = "7900",
                onTextChanged = {},
                isError = true
            )
        }
    }
}
