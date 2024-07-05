package com.hankki.feature.home.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun DropdownFilterChip(
    chipState: ChipState,
    title: String,
    menus: List<String> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onClickMenu: () -> Unit = {},
    onClickChip: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    HankkiFilterChip(
        chipState = chipState,
        title = title,
        onClick = {
            onClickChip()
            expanded = true
        }
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box {
                Popup(
                    onDismissRequest = {
                        onDismissRequest()
                        expanded = false
                    },
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
                                modifier = Modifier.fillMaxWidth(),
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
        }
    }
}
