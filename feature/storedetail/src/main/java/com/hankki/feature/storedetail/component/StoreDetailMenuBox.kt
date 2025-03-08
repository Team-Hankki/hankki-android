package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import kotlinx.collections.immutable.PersistentList

@Composable
fun StoreDetailMenuBox(
    menuItems: PersistentList<MenuItem>,
    onMenuEditClick: () -> Unit
) {
    Spacer(modifier = Modifier.padding(top = 10.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 22.dp, vertical = 20.dp)
            .wrapContentSize()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "메뉴 ",
                style = HankkiTheme.typography.sub2,
                color = Gray900
            )
            Text(
                text = menuItems.size.toString(),
                style = HankkiTheme.typography.sub2,
                color = Red500
            )
        }

        Spacer(modifier = Modifier.padding(top = 18.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            menuItems.forEachIndexed { index, item ->
                StoreDetailItem(name = item.name, price = item.price.toString())

                if (index != menuItems.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(23.dp))

        StoreDetailModDeleteButton(
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.hankki.feature.storedetail.R.drawable.ic_bottom_edit),
                    contentDescription = "추가 아이콘",
                    tint = Color.Unspecified
                )
            },
            content = {
                Text(
                    text = stringResource(id = R.string.menu_mod_delete_report),
                    style = HankkiTheme.typography.body5,
                    color = Gray600
                )
            },
            onClick = onMenuEditClick
        )
    }
}
