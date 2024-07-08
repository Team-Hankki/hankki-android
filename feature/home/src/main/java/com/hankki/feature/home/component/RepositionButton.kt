package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.White

@Composable
fun RepositionButton(
    height: Dp,
    onClick: () -> Unit = {},
) {
    Column {
        Box(
            modifier = Modifier
                .padding(end = 21.dp, bottom = 14.dp)
                .size(38.dp)
                .clip(CircleShape)
                .border(1.dp, Gray300, CircleShape)
                .background(White)
                .padding(9.dp)
                .noRippleClickable(onClick = onClick)
        ) {
            AsyncImage(
                model = R.drawable.ic_map_here,
                contentDescription = "here"
            )
        }
        Spacer(modifier = Modifier.height(height))
    }
}
