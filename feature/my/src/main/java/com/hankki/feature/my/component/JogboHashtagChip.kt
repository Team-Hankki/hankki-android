package com.hankki.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun JogboHashtagChip(chiptext: String) {
    Text(
        text = chiptext,
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Gray100)
            .border(1.dp, Gray200, RoundedCornerShape(100.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        color = Gray500,
        style = HankkiTheme.typography.caption1
    )
}
