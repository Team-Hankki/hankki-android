package com.hankki.core.designsystem.component.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun StoreDetailItem(item: Pair<String, String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.first,
            style = HankkiTheme.typography.body1,
            modifier = Modifier.weight(1f)
        )

        DottedLine(
            color = Gray100,
            width = 2.dp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = item.second,
            style = HankkiTheme.typography.body2,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun DottedLine(color: Color, width: Dp, modifier: Modifier = Modifier) {
    val dotRadius = width / 2
    val dotSpacing = 6.dp

    Canvas(modifier) {
        val circle = Path()
        circle.addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        val pathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
            cap = StrokeCap.Round,
            strokeWidth = dotRadius.toPx()
        )
    }
}
