package com.hankki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun StoreNameSearchButton() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Gray100)
            .padding(vertical = 12.dp)
            .padding(start = 10.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search icon",
            tint = Gray300
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "이름으로 검색",
            style = HankkiTheme.typography.body6,
            color = Gray400
        )
    }
}

@Preview
@Composable
fun StoreNameSearchButtonPreview() {
    HankkijogboTheme {
        StoreNameSearchButton()
    }
}
