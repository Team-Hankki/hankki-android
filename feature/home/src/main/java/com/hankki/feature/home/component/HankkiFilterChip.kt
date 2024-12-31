package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red100
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiFilterChip(
    text: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    textStyle: TextStyle = HankkiTheme.typography.suitCaption,
    onClick: () -> Unit = {},
) {
    val (textColor, borderColor, containerColor) = if (isSelected) {
        listOf(Red500, Red500, Red100)
    } else {
        listOf(Gray600, Gray300, White)
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            )
            .background(containerColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .noRippleClickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview
@Composable
fun HankkiFilterChipPreview() {
    HankkijogboTheme {
        Column {
            HankkiFilterChip(text = "최신순", isSelected = true)
            HankkiFilterChip(text = "가격순", isSelected = false)
        }
    }
}
