package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red

@Composable
fun HankkiTitleTextField(
    title: String,
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    isFocused: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = stringResource(id = R.string.enable_under_eight_thousand_won),
    tailingIcon: @Composable () -> Unit = {},
) {
    val titleColor = when {
        isError -> Red
        isFocused -> Gray900
        else -> Gray500
    }

    val borderColor = when {
        isError -> Red
        isFocused -> Gray850
        else -> Gray300
    }

    val textColor = when {
        isError -> Red
        isFocused -> Gray900
        else -> Gray400
    }

    Column {
        Row {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = HankkiTheme.typography.body5,
                color = titleColor
            )
        }

        Spacer(modifier = Modifier.height(3.dp))

        HankkiTextField(
            value = value,
            placeholder = placeholder,
            onTextChanged = onTextChanged,
            borderColor = borderColor,
            textColor = textColor,
            tailingIcon = tailingIcon
        )

        if (isError) {
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = errorMessage,
                    style = HankkiTheme.typography.caption1,
                    color = Red
                )
            }
        }
    }
}

@Composable
fun HankkiMenuTextField(
    title: String,
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    HankkiTitleTextField(
        title = title,
        value = value,
        placeholder = "예) 된장찌개",
        onTextChanged = onTextChanged,
        isFocused = isFocused,
    )
}

@Composable
fun HankkiPriceTextField(
    title: String,
    value: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    isFocused: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    HankkiTitleTextField(
        title = title,
        value = value,
        placeholder = placeholder,
        onTextChanged = onTextChanged,
        isFocused = isFocused,
        isError = isError,
    ){
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
                onTextChanged = {},
                isFocused = false
            )
            HankkiMenuTextField(
                title = "메뉴 이름",
                value = "김치찌개",
                placeholder = "예) 된장찌개",
                onTextChanged = {},
                isFocused = false
            )
            HankkiMenuTextField(
                title = "메뉴 이름",
                value = "김치찌개",
                placeholder = "예) 된장찌개",
                onTextChanged = {},
                isFocused = true
            )

            HankkiPriceTextField(
                title = "가격",
                value = "",
                placeholder = "7900",
                onTextChanged = {},
                isError = false,
                isFocused = false
            )

            HankkiPriceTextField(
                title = "가격",
                value = "8000",
                placeholder = "7900",
                onTextChanged = {},
                isError = false,
                isFocused = true
            )

            HankkiPriceTextField(
                title = "가격",
                value = "9000",
                placeholder = "7900",
                onTextChanged = {},
                isError = true,
                isFocused = false
            )
        }
    }
}
