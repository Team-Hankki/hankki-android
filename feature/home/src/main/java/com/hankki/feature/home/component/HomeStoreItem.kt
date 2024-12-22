package com.hankki.feature.home.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.item.StoreItem
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.home.R

@Composable
fun HomeStoreItem(
    storeId: Long,
    storeImageUrl: String?,
    category: String,
    storeName: String,
    price: Int,
    heartCount: Int,
    modifier: Modifier = Modifier,
    onClickItem: (Long) -> Unit = {},
    onPlusClick: () -> Unit = {},
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
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_jogbo_folder),
            contentDescription = "plus button",
            modifier = Modifier.noRippleClickable(onClick = onPlusClick),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun StoreItemPreview() {
    HankkijogboTheme {
        HomeStoreItem(
            storeId = 1,
            storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
            category = "한식",
            storeName = "한끼네 한정식",
            price = 7900,
            heartCount = 300
        )
    }
}
