package com.hankki.feature.home.component

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.home.model.ChipState

@Composable
fun HankkiStateChip(
    chipState: ChipState,
    defaultTitle: String,
    @DrawableRes imageResource: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    filterContent: @Composable ColumnScope.() -> Unit = {},
) {
    Column {
        Box(
            modifier = modifier
                .bounceClick(
                    scaleDown = 0.88f,
                    onClick = onClick
                )
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = chipState.style.borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .background(color = chipState.style.containerColor)
                .padding(top = 6.dp, bottom = 6.dp, start = 8.dp, end = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (chipState !is ChipState.Fixed) {
                    Icon(
                        imageVector = ImageVector.vectorResource(imageResource),
                        contentDescription = "icon",
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(16.dp),
                        tint = Color.Unspecified
                    )
                }

                Text(
                    text = when (chipState) {
                        is ChipState.Selected -> defaultTitle
                        is ChipState.Unselected -> defaultTitle
                        is ChipState.Fixed -> chipState.title
                    },
                    style = HankkiTheme.typography.caption1,
                    color = chipState.style.labelColor
                )
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = when (chipState) {
                            is ChipState.Selected -> com.hankki.feature.home.R.drawable.ic_arrow_up_18
                            is ChipState.Unselected -> com.hankki.feature.home.R.drawable.ic_arrow_down_18
                            is ChipState.Fixed -> R.drawable.ic_x
                        }
                    ),
                    contentDescription = "icon",
                    modifier = Modifier.size(18.dp),
                    tint = chipState.style.iconColor
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
        ChipState.Selected(),
        "한식",
        R.drawable.ic_x,
    )
}
