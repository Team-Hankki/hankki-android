package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.home.model.ChipState

@Composable
fun HankkiStateChip(
    chipState: ChipState,
    defaultTitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    filterContent: @Composable ColumnScope.() -> Unit = {},
) {
    Column {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(100.dp))
                .border(
                    width = 1.dp,
                    color = chipState.borderColor,
                    shape = RoundedCornerShape(100.dp)
                )
                .background(color = chipState.containerColor)
                .padding(top = 4.dp, bottom = 4.dp, start = 12.dp, end = 4.dp)
                .noRippleClickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = when (chipState) {
                        ChipState.SELECTED -> defaultTitle
                        ChipState.UNSELECTED -> defaultTitle
                        ChipState.FIXED -> chipState.title
                    },
                    style = HankkiTheme.typography.caption1,
                    color = chipState.labelColor
                )
                AsyncImage(
                    model = when (chipState) {
                        ChipState.SELECTED -> R.drawable.ic_arrow_up
                        ChipState.UNSELECTED -> R.drawable.ic_arrow_down
                        ChipState.FIXED -> R.drawable.ic_x
                    },
                    contentDescription = "icon",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(chipState.iconColor),
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        filterContent()
    }
}

@Preview
@Composable
fun CustomChipPreview() {
    HankkiStateChip(
        ChipState.SELECTED,
        "한식"
    )
}
