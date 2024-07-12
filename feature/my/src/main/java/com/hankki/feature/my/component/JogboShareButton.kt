package com.hankki.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun JogboShareButton() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(88.dp))
            .background(Gray800)
            .padding(top = 7.dp, bottom = 9.dp, start = 9.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = "",
            tint = Red
        )
        Text(text = "공유", style = HankkiTheme.typography.body3, color = White)
    }
}