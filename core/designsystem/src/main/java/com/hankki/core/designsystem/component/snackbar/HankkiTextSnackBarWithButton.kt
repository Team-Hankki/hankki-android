package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun HankkiTextSnackBarWithButton(onClick: () -> Unit) {
    val message = "나의 족보에 추가되었습니다."
    val buttonText = "보기"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        HankkiSnackBar {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = message, color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = buttonText,
                    style = HankkiTheme.typography.body3,
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.noRippleClickable(onClick = onClick)
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
