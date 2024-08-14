package com.hankki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.Red400
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .run {
                if (enabled) bounceClick(
                    radius = 16f,
                    onClick = onClick
                )
                else this
            }
            .clip(RoundedCornerShape(16.dp))
            .background(if (enabled) Red500 else Red400)
            .padding(vertical = 15.dp, horizontal = 22.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = White
        )
    }
}

@Composable
fun HankkiTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color? = null,
    textStyle: TextStyle = TextStyle.Default,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .run {
                if (enabled) noRippleClickable(onClick = onClick)
                else this
            }
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor ?: Color.Transparent)
            .padding(vertical = 15.dp, horizontal = 22.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = Red500
        )
    }
}

@Composable
fun HankkiOnboardingBlackButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .run {
                if (enabled) bounceClick(onClick = onClick)
                else this
            }
            .clip(RoundedCornerShape(16.dp))
            .background(Gray800)
            .padding(vertical = 13.dp, horizontal = 38.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = White
        )
    }
}

@Composable
fun HankkiOnboardingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .run {
                if (enabled) bounceClick(
                    radius = 16f,
                    onClick = onClick
                )
                else this
            }
            .clip(RoundedCornerShape(16.dp))
            .background(if (enabled) Red500 else Red400)
            .padding(vertical = 13.dp, horizontal = 38.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = White
        )
    }
}

@Preview
@Composable
fun HankkiButtonPreview() {
    HankkijogboTheme {
        Column {
            HankkiButton(text = "로그아웃", onClick = {})
            HankkiTextButton(text = "돌아가기", onClick = {})
        }
    }
}
