package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiWhiteSnackBarWithButton(onClick: () -> Unit) {
    val message = "나의 족보에 추가했어요"
    val buttonText = "보기"

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(0.65f)
            .padding(vertical = 14.dp)
            .clip(RoundedCornerShape(65.dp))
            .background(color = White)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = Gray900,
                style = HankkiTheme.typography.sub3,
                modifier = Modifier.padding(start = 22.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = buttonText,
                color = Red,
                style = HankkiTheme.typography.sub3,
                modifier = Modifier
                    .noRippleClickable(onClick = onClick)
                    .padding(end = 12.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewHankkiWhiteSnackBarWithButton() {
    // HankkiTextSnackBarWithButton(onClick = { /* 클릭 시 실행될 코드 */ })
}
