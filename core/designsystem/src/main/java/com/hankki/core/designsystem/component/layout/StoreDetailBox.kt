package com.hankki.core.designsystem.component.layout

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
import com.hankki.core.designsystem.component.chip.HankkiCategoryChip
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreDetailMenuBox(
    title: String,
    tag: String,
    menuItems: List<Pair<String, String>>,
    leadingButton1: @Composable () -> Unit,
    leadingButton2: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
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
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            HankkiCategoryChip(text = tag)
        }

        Spacer(modifier = Modifier.height(32.dp))

        menuItems.forEach { item ->
            StoreDetailItem(item = item)
        }

        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = "메뉴 추가/지우기",
            style = HankkiTheme.typography.sub3,
            color = Gray400,
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(38.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leadingButton1()
            leadingButton2()
        }
    }
}
