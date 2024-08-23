package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiWhiteSnackBarWithButton(
    message: String,
    buttonText: String = "보기",
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(65.dp))
            .shadow(18.dp, shape = RoundedCornerShape(65.dp))
            .background(color = White)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 14.dp)
                .padding(start = 22.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = Gray900,
                style = HankkiTheme.typography.sub3,
                modifier = Modifier.weight(1f, false)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = buttonText,
                color = Red500,
                style = HankkiTheme.typography.sub3,
                modifier = Modifier
                    .noRippleClickable(onClick = onClick)
                    .padding(horizontal = 12.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewHankkiWhiteSnackBarWithButton() {
    HankkijogboTheme {
        HankkiWhiteSnackBarWithButton(
            message = "내 족보에 추가했어요",
            onClick = { /* 클릭 시 실행될 코드 */ }
        )
    }
}
