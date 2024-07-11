package com.hankki.feature.my

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.component.StoreItem

@Composable
fun MyStoreRoute() {
    MyStoreScreen()
}

@Composable
fun MyStoreScreen() {
    LazyColumn(modifier = Modifier.padding(start = 22.dp, end = 11.dp)) {
        items(5) {
            StoreItem(
                storeImageUrl = "",
                category = "한식",
                storeName = "한끼네 한정식",
                price = 7900,
                heartCount = 300,
                isIconUsed = false
            )
        }

    }
}

@Preview
@Composable
fun MyStoreScreenPreview(){
    HankkijogboTheme {
        MyStoreScreen()
    }
}