package com.hankki.feature.storedetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.formatPrice
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun StoreDetailItem(name: String, price: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp),
    ) {
        Text(
            text = name,
            style = HankkiTheme.typography.body8,
            overflow = TextOverflow.Ellipsis,
            color = Gray900
        )

        Text(
            text = "${price.formatPrice()}원",
            style = HankkiTheme.typography.body5,
            color = Gray850,
            textAlign = TextAlign.End
        )
    }
}
