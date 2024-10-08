package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.formatPrice
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red100
import com.hankki.core.designsystem.theme.Red500
import com.hankki.domain.storedetail.entity.MenuItem

@Composable
fun MenuItemComponent(
    menuItem: MenuItem,
    selectedMenu: String?,
    onMenuSelected: (String) -> Unit
) {
    val isSelected = selectedMenu == menuItem.name
    val backgroundColor = if (isSelected) Red100 else Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 18.5.dp, horizontal = 22.dp)
            .noRippleClickable { onMenuSelected(menuItem.name) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                painter = painterResource(
                    id = if (isSelected) com.hankki.core.designsystem.R.drawable.ic_btn_radio_check
                    else com.hankki.core.designsystem.R.drawable.ic_btn_radio_uncheck
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.padding(start = 13.dp))
            Text(
                text = menuItem.name,
                style = HankkiTheme.typography.body3,
                color = if (selectedMenu == menuItem.name) Red500 else Gray700
            )
        }
        Text(
            text = "${menuItem.price.toString().formatPrice()}원",
            style = HankkiTheme.typography.body3,
            color = if (selectedMenu == menuItem.name) Red500 else Gray700
        )
    }
}
