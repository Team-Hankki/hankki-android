package com.hankki.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.amplitude.PropertyKey
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.model.ChipItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HankkiFilterBottomSheet(
    priceFilter: ImmutableList<ChipItem>,
    sortFilter: ImmutableList<ChipItem>,
    selectedPriceItem: ChipItem,
    selectedSortItem: ChipItem,
    onDismissRequest: () -> Unit = {},
    onSubmit: (ChipItem, ChipItem) -> Unit = { _, _ -> },
) {
    val tracker = LocalTracker.current

    val nowPriceName = remember { mutableStateOf(selectedPriceItem) }
    val nowSortedName = remember { mutableStateOf(selectedSortItem) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        containerColor = White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp)
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            FilterContent(
                title = "가격대",
                filters = priceFilter,
                tag = nowPriceName.value.tag,
                onClick = {
                    nowPriceName.value = it
                }
            )

            FilterContent(
                title = "정렬",
                filters = sortFilter,
                tag = nowSortedName.value.tag,
                onClick = {
                    nowSortedName.value = it
                }
            )
        }

        HorizontalDivider(
            color = Gray200
        )

        Text(
            text = "적용",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 4.dp)
                .noRippleClickable {
                    onSubmit(nowPriceName.value, nowSortedName.value)
                    tracker.track(
                        type = EventType.COMPLETED,
                        name = "Home_Detail_Filter",
                        properties = mapOf(
                            PropertyKey.ARRAY to nowSortedName.value.name,
                            PropertyKey.PRICE to nowPriceName.value.name
                        )
                    )
                },
            color = Gray800,
            textAlign = TextAlign.Center,
            style = HankkiTheme.typography.body4
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterContent(
    title: String,
    filters: ImmutableList<ChipItem>,
    tag: String,
    onClick: (ChipItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, bottom = 4.dp),
            color = Gray850,
            style = HankkiTheme.typography.body5
        )
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach {
                key(it.tag) {
                    HankkiFilterChip(
                        text = it.name,
                        textStyle = HankkiTheme.typography.caption1,
                        isSelected = tag == it.tag,
                        onClick = { onClick(it) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Preview(showBackground = true)
@Composable
private fun HankkiFilterBottomSheetPreview() {
    HankkijogboTheme {
        HankkiFilterBottomSheet(
            selectedPriceItem = ChipItem(name = "", tag = ""),
            selectedSortItem = ChipItem(name = "", tag = ""),
            priceFilter = persistentListOf(),
            sortFilter = persistentListOf(),
        )
    }
}
