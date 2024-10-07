package com.hankki.feature.my.newjogbo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.designsystem.theme.Red400
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White

@Composable
fun NewJogboLongButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle.Default,
    backgroundColor: Color = if (enabled) Red500 else Red400,
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
            .background(backgroundColor)
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = White
        )
    }
}
