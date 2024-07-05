package com.hankki.feature.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiFilterChip(
    chipState: ChipState,
    defaultTitle: String,
    menus: List<String> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onClickMenu: (String) -> Unit = {},
    onClickChip: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    HankkiStateChip(
        chipState = chipState,
        defaultTitle = defaultTitle,
        onClick = onClickChip
    ) {
        AnimatedVisibility(
            visible = chipState == ChipState.SELECTED,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Popup(
                onDismissRequest = onDismissRequest,
                content = content
            )
        }
    }
}

@Composable
fun DropdownFilterChip(
    chipState: ChipState,
    defaultTitle: String,
    menus: List<String> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onClickMenu: (String) -> Unit = {},
    onClickChip: () -> Unit = {},
) {
    HankkiFilterChip(
        chipState = chipState,
        defaultTitle = defaultTitle,
        menus = menus,
        onDismissRequest = onDismissRequest,
        onClickMenu = onClickMenu,
        onClickChip = onClickChip
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Gray200, RoundedCornerShape(10.dp))
                .background(White)
                .width(IntrinsicSize.Max)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            menus.forEach { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .noRippleClickable(onClick = { onClickMenu(item) }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        style = HankkiTheme.typography.caption1,
                        color = Gray600
                    )
                }
                if (item != menus.last()) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Gray200
                    )
                }
            }
        }
    }
}

@Composable
fun RowFilterChip(
    chipState: ChipState,
    defaultTitle: String,
    menus: List<String> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onClickMenu: (String) -> Unit = {},
    onClickChip: () -> Unit = {},
) {
    HankkiFilterChip(
        chipState = chipState,
        defaultTitle = defaultTitle,
        menus = menus,
        onDismissRequest = onDismissRequest,
        onClickMenu = onClickMenu,
        onClickChip = onClickChip
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            item {
                Spacer(modifier = Modifier.width(22.dp))
            }
            items(menus) { menu ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(White)
                        .noRippleClickable(onClick = { onClickMenu(menu) }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = menu,
                        style = HankkiTheme.typography.caption1,
                        color = Gray400
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.width(22.dp))
            }
        }
    }
}
