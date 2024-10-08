package com.hankki.feature.storedetail.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.formatPrice
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun StoreDetailItem(name: String, price: String) {
    val formattedPrice = price.formatPrice()

    val minPadding = 5.dp
    val maxPadding = 27.dp
    val maxNameLength = 10
    val displayName = if (name.length > maxNameLength) {
        name.take(maxNameLength) + "..."
    } else {
        name
    }

    val calculatedPadding = if (name.length <= maxNameLength) {
        maxPadding - ((name.length - 1) * 2).dp
    } else {
        minPadding
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = displayName,
            style = HankkiTheme.typography.sub3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.wrapContentWidth(),
            color = Gray700,
        )

        Spacer(modifier = Modifier.width(calculatedPadding))
        DottedLine(
            color = Gray100,
            width = 2.dp,
            modifier = Modifier
                .defaultMinSize(minWidth = 5.dp)
                .weight(1f, fill = true)
        )
        Spacer(modifier = Modifier.width(calculatedPadding))

        Text(
            text = formattedPrice + "원",
            style = HankkiTheme.typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            textAlign = TextAlign.End,
            color = Gray500,
            modifier = Modifier.wrapContentWidth(align = Alignment.End)
        )
    }
}

@Composable
fun DottedLine(color: Color, width: Dp, modifier: Modifier = Modifier) {
    val dotRadius = width / 2
    val dotSpacing = 6.dp

    Canvas(modifier) {
        val circle = Path().apply {
            addOval(Rect(center = Offset.Zero, radius = dotRadius.toPx()))
        }
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
