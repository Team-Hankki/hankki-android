package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiTopBar(
    title: String,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(White),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = HankkiTheme.typography.suitH1,
            color = Gray900
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_dropdown_btn),
            contentDescription = "button",
            modifier = Modifier.noRippleClickable(onClick = onClick),
            tint = Gray300
        )
    }
}
