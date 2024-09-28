package com.hankki.feature.report.searchstore.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.Red100
import com.hankki.core.designsystem.theme.White

@Composable
fun LocationItem(
    location: String,
    address: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(if (isSelected) Red100 else White)
            .padding(vertical = 24.dp)
            .padding(start = 22.dp, end = 14.dp)
            .noRippleClickable(onClick)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = location,
                style = HankkiTheme.typography.suitSub2,
                color = if (isSelected) Red500 else Gray900
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = address,
                style = HankkiTheme.typography.body8,
                color = Gray400
            )
        }
        if (isSelected) {
            Icon(
                painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_check_btn),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationItemPreview() {
    HankkijogboTheme {
        LocationItem(
            location = "한끼네 한정식",
            address = "서울시 강남구 역삼동 123-456",
            isSelected = true,
            onClick = {}
        )
    }
}
