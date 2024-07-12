package com.hankki.feature.report.searchstore.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun LocationItem(
    location: String,
    address: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 24.dp)
            .noRippleClickable(onClick)
    ) {
        Text(
            text = location,
            style = HankkiTheme.typography.suitSub1,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = address,
            style = HankkiTheme.typography.body5,
            color = Gray400
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationItemPreview() {
    HankkijogboTheme {
        LocationItem(
            location = "한끼네 한정식",
            address = "서울시 강남구 역삼동 123-456",
            onClick = {}
        )
    }
}
