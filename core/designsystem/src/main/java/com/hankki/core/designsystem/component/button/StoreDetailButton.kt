package com.hankki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.graphics.Color
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreDetailButton(
    leadingIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    tailingIcon: @Composable () -> Unit = {},
    isSelected: Boolean = false,
    backgroundColor: Color = White,
    borderColor: Color = if (isSelected) Red else Gray200
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .background(color = White)
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 15.5.dp)
    ) {
        Spacer(modifier = Modifier.height(11.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
            content()
            Spacer(modifier = Modifier.width(8.dp))
            tailingIcon()
        }
    }
}
