package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.White

@Composable
fun RepositionButton(
    height: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column {
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Gray300, CircleShape)
                .background(White)
                .noRippleClickable(onClick = onClick)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_map_here),
                contentDescription = "reposition user location",
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(height))
    }
}
