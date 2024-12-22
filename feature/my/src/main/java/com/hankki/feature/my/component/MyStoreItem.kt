package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.item.StoreItem
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500

@Composable
fun MyStoreItem(
    storeId: Long,
    storeImageUrl: String?,
    category: String,
    storeName: String,
    price: Int,
    heartCount: Int,
    isIconUsed: Boolean,
    isIconSelected: Boolean,
    modifier: Modifier = Modifier,
    onClickItem: (Long) -> Unit = {},
    onLongClickItem: ((Long) -> Unit)? = null,
    editSelected: () -> Unit = {},
) {
    StoreItem(
        storeId = storeId,
        storeImageUrl = storeImageUrl,
        category = category,
        storeName = storeName,
        price = price,
        heartCount = heartCount,
        modifier = modifier,
        onClickItem = onClickItem,
        onLongClickItem = onLongClickItem,
    ) {
        if (isIconUsed) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart_filled),
                contentDescription = "plus button",
                modifier = Modifier
                    .size(52.dp)
                    .noRippleClickable(onClick = editSelected),
                colorFilter = ColorFilter.tint(if (isIconSelected) Red500 else Gray200)
            )
        }
    }
}

@Preview
@Composable
fun StoreItemPreview() {
    HankkijogboTheme {
        MyStoreItem(
            storeId = 1,
            storeImageUrl = "",
            category = "한식",
            storeName = "한끼네 한정식",
            price = 7900,
            heartCount = 300,
            isIconUsed = true,
            isIconSelected = true,
            editSelected = {}
        )
    }
}
