package com.hankki.feature.my.myjogbo.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun JogboItemText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = HankkiTheme.typography.sub3,
        color = color,
        textAlign = TextAlign.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 14.dp,
                start = 17.dp,
                end = 22.dp
            ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@Preview
fun JogboItemTextPreview() {
    HankkijogboTheme {
        JogboItemText(
            text = "나의 족보",
            color = Gray800
        )
    }
}
