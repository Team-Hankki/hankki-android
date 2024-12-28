package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Red100
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreDetailMenuButton(
    leadingIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    tailingIcon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .noRippleClickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth()
        ) {
            leadingIcon()
            Spacer(modifier = Modifier.width(4.dp))
            content()
            Spacer(modifier = Modifier.width(4.dp))
            tailingIcon()
        }
    }
}

@Composable
fun StoreDetailReportButton(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    tailingIcon: @Composable () -> Unit = {},
    isSelected: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isSelected) Red100 else White)
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 15.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                content()
            }
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterEnd
            ) {
                tailingIcon()
            }
        }
    }
}

@Composable
fun StoreDetailDifferentButton(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    tailingIcon: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(23.dp))
            .background(color = White)
            .noRippleClickable(onClick = onClick)
            .padding(start = 9.5.dp, end = 5.5.dp)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            leadingIcon()
        }

        Spacer(modifier = Modifier.padding(start = 4.dp))
        Box(
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }

        Spacer(modifier = Modifier.padding(start = 2.dp))
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            tailingIcon()
        }
    }
}

@Composable
fun StoreDetailModDeleteButton(
    content: @Composable () -> Unit,
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = Gray100)
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 77.5.dp, vertical = 13.5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.CenterEnd
            ) {
                leadingIcon()
            }

            Spacer(modifier = Modifier.padding(start = 4.dp))
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                content()
            }
        }
    }
}
