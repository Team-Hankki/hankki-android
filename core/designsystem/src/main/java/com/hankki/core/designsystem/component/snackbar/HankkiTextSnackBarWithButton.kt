package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiTextSnackBarWithButton(onClick: () -> Unit) {
    val message = "나의 족보에 추가되었습니다."
    val buttonText = "보기"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
    ) {
        HankkiSnackBar {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = HankkiTheme.typography.body3,
                    color = White,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buttonText,
                    style = HankkiTheme.typography.body3,
                    color = White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .noRippleClickable(onClick = onClick)
                        .widthIn(min = 40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHankkiTextSnackBarWithButton() {
    HankkiTextSnackBarWithButton(onClick = { /* 클릭 시 실행될 코드 */ })
}
