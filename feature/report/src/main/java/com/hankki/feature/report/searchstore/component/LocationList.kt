package com.hankki.feature.report.searchstore.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.feature.report.model.LocationModel
import kotlinx.collections.immutable.PersistentList

@Composable
fun LocationList(
    selectedLocation: LocationModel?,
    locations: PersistentList<LocationModel>,
    onClick: (LocationModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(locations) { item ->
            LocationItem(
                location = item.location,
                address = item.address,
                isSelected = (item === selectedLocation),
                onClick = { onClick(item) }
            )

            if (item != locations.last()) {
                HorizontalDivider(color = Gray100)
            }
        }
        item {
            BottomBlurLayout()
        }
    }
}
