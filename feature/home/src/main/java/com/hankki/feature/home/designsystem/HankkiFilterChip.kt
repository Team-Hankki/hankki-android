package com.hankki.feature.home.designsystem

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.SubColor01
import com.hankki.core.designsystem.theme.SubColor02
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiFilterChip(
    chipState: ChipState,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    filterContent: @Composable ColumnScope.() -> Unit = {},
) {
    val icon = remember {
        when (chipState) {
            ChipState.SELECTED -> R.drawable.ic_arrow_up
            ChipState.UNSELECTED -> R.drawable.ic_arrow_down
            ChipState.FIXED -> R.drawable.ic_x
        }
    }

    Column {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(100.dp))
                .border(1.dp, chipState.borderColor, RoundedCornerShape(100.dp))
                .background(chipState.containerColor)
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
                    text = title,
                    style = HankkiTheme.typography.caption1,
                    color = chipState.labelColor
                )
                AsyncImage(
                    model = icon,
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

enum class ChipState(
    val containerColor: Color,
    val borderColor: Color,
    val labelColor: Color,
    val iconColor: Color,
) {
    SELECTED(
        containerColor = White,
        borderColor = Gray300,
        labelColor = Gray600,
        iconColor = Gray600
    ),
    UNSELECTED(
        containerColor = White,
        borderColor = Gray300,
        labelColor = Gray400,
        iconColor = Gray400
    ),
    FIXED(
        containerColor = SubColor01,
        borderColor = SubColor02,
        labelColor = Gray600,
        iconColor = Gray600
    )
}

@Preview
@Composable
fun CustomChipPreview() {
    HankkiFilterChip(
        ChipState.SELECTED,
        "한식"
    )
}
