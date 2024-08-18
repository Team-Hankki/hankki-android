package com.hankki.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R

@Composable
fun RepositionButton(
    height: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_map_here),
            contentDescription = "reposition user location",
            modifier = modifier
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color.Black.copy(alpha = 0.25f),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                )
                .noRippleClickable(onClick = onClick)
        )
        Spacer(modifier = Modifier.height(height))
    }
}
