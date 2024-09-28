package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White


@Composable
fun HankkiTextSnackBarWithButton(
    message: String,
    buttonText: String = "보기",
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        HankkiSnackBar {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = HankkiTheme.typography.body5,
                    color = White,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = buttonText,
                    style = HankkiTheme.typography.body6,
                    color = White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .noRippleClickable(onClick = onClick)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHankkiTextSnackBarWithButton() {
    HankkijogboTheme {
        HankkiTextSnackBarWithButton(
            message = "테스트 텍스트"
        ) {

        }
    }
}
