package com.hankki.feature.storedetail.component


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
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.Red100

@Composable
fun HankkiStoreDetailCategoryChip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = HankkiTheme.typography.caption1,
    containerColor: Color = Red100,
    labelColor: Color = Red500,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = labelColor
        )
    }
}