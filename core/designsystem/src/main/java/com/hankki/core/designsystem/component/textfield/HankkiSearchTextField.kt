package com.hankki.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun HankkiSearchTextField(
    value: String,
    onTextChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    clearText: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "식당 이름으로 검색하기",
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = when {
        isFocused -> Gray850
        else -> Color.Transparent
    }

    HankkiTextField(
        value = value,
        placeholder = "식당 이름으로 검색하기",
        onTextChanged = onTextChanged,
        borderColor = borderColor,
        textColor = Gray800,
        backgroundColor = Gray100,
        onFocusChanged = { isFocused = it },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        leadingIcon = {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search icon",
                    tint = Gray300
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        },
        tailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_dark_x),
                    contentDescription = "clear icon",
                    modifier = Modifier
                        .size(32.dp)
                        .noRippleClickable(clearText),
                    tint = Color.Unspecified
                )
            }
        }
    )
}

@Preview
@Composable
fun HankkiSearchTextFieldPreview() {
    HankkijogboTheme {
        HankkiSearchTextField(
            value = "",
            onTextChanged = {},
            onFocusChanged = {},
            clearText = {}
        )
    }
}
