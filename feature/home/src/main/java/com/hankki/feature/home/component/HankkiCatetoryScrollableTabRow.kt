package com.hankki.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.R
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.HomeChips
import kotlinx.collections.immutable.PersistentList

@Composable
fun HankkiCategoryScrollableTabRow(
    categoryChipItems: PersistentList<CategoryChipItem>,
    onClickItem: (HomeChips, String, tag: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickFilterButton: () -> Unit,
) {
    val currentDensity = LocalDensity.current

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var tabRowHeight by remember { mutableStateOf(0.dp) }

    if (categoryChipItems.isNotEmpty()) {
        Box {
            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                modifier = modifier,
                containerColor = White,
                contentColor = White,
                edgePadding = edgePadding,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                tabRowHeight = with(currentDensity) {
                                    coordinates.size.height.toDp()
                                }
                            }
                            .tabIndicatorOffset(tabPositions[selectedIndex])
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(0xFF424242),
                                        Color(0xFF090909),
                                    )
                                )
                            ),
                        color = Color.Transparent,
                        height = 2.dp
                    )
                },
            ) {
                categoryChipItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            onClickItem(
                                HomeChips.CATEGORY,
                                item.name,
                                item.tag
                            )
                        },
                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = "filter",
                            modifier = Modifier
                                .padding(horizontal = 14.dp)
                                .size(40.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.name,
                            style = if (selectedIndex == index) HankkiTheme.typography.caption3 else HankkiTheme.typography.caption1,
                            color = Gray900,
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
                WhiteGradientBox(
                    modifier = Modifier.size(tabRowHeight - edgePadding)
                )
            }
            FilterButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                size = tabRowHeight,
            ) {
                onClickFilterButton()
            }
        }
    }
}

private val edgePadding = 12.dp

@Composable
private fun WhiteGradientBox(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0x00FFFFFF),
                        White,
                    ),
                    start = Offset(0.0f, 0.0f),
                    end = Offset(80.0f, 0.0f)
                )
            )
    )
}

@Composable
private fun FilterButton(
    size: Dp,
    modifier: Modifier = Modifier,
    onClickFilterButton: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        WhiteGradientBox(
            modifier = Modifier.size(
                width = size + edgePadding,
                height = size
            )
        )

        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter_btn),
                    contentDescription = "reposition user location",
                    modifier = modifier
                        .size(size - 20.dp)
                        .noRippleClickable(onClickFilterButton)
                        .shadow(
                            elevation = 6.dp,
                            shape = CircleShape,
                            spotColor = Color.Black.copy(alpha = 0.25f),
                            ambientColor = Color.Black.copy(alpha = 0.25f),
                        )
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.padding(end = 12.dp))
        }
    }
}