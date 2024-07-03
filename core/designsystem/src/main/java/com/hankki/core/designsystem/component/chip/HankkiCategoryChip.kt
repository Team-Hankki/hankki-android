package com.hankki.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.PrimaryColor
import com.hankki.core.designsystem.theme.SubColor03

@Composable
fun HankkiCategoryChip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = HankkiTheme.typography.caption2,
    containerColor: Color = SubColor03,
    labelColor: Color = PrimaryColor,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = labelColor
        )
    }
}

@Preview
@Composable
fun HankkiCategoryChipPreview() {
    HankkijogboTheme {
        HankkiCategoryChip(text = "한식")
    }
}
