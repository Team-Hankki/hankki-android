package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.chip.HankkiCategoryChip
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import kotlinx.collections.immutable.PersistentList

@Composable
fun StoreDetailMenuBox(
    title: String,
    tag: String,
    menuItems: PersistentList<MenuItem>,
    likeButton: @Composable () -> Unit,
    addMyJogboButton: @Composable () -> Unit,
    onMenuEditClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .padding(28.dp)
            .wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = HankkiTheme.typography.suitH1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Gray900
            )
            Spacer(modifier = Modifier.width(8.dp))
            HankkiCategoryChip(text = tag)
        }

        Spacer(modifier = Modifier.padding(top = 30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            menuItems.forEach { item ->
                StoreDetailItem(name = item.name, price = item.price.toString())
            }

        }

        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = "메뉴 추가/수정하기",
            style = HankkiTheme.typography.caption1,
            color = Gray400,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.noRippleClickable(onClick = onMenuEditClick)
        )

        Spacer(modifier = Modifier.height(38.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            likeButton()
            Spacer(modifier = Modifier.width(13.dp))
            addMyJogboButton()
        }
    }
}
