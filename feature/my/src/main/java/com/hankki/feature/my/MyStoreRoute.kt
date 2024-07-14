package com.hankki.feature.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.StoreEntity
import com.hankki.feature.my.component.StoreItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyStoreRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    type: String,
    myStoreViewMidel: MyStoreViewModel = hiltViewModel()
) {
    val myStoreState by myStoreViewMidel.myStoreState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myStoreViewMidel.getMockStoreList()
    }

    MyStoreScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        type = type,
        storeItems = myStoreState.myStoreItems,
        updateStoreSeleted = { index, isjogboSelected ->
            myStoreViewMidel.updateStoreSeleted(
                index,
                isjogboSelected
            )
        }
    )
}

@Composable
fun MyStoreScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    type: String,
    storeItems: PersistentList<MyStoreModel>,
    updateStoreSeleted: (Int, Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .padding((paddingValues))
            .background(White)
    ) {
        HankkiTopBar(
            modifier = Modifier.background(White),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(onClick = navigateUp)
                )
            },
            content = {
                Text(
                    text = if (type == "like") stringResource(id = R.string.description_store_like) else stringResource(
                        id = R.string.description_store_report
                    ),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )
        LazyColumn(
            modifier = Modifier
                .padding(start = 22.dp, end = 11.dp)
        ) {
            items(storeItems.size) { index ->
                val store = storeItems[index]

                store.isLiked?.let {
                    StoreItem(
                        imageUrl = store.imageURL,
                        category = store.category,
                        name = store.name,
                        price = store.lowestPrice,
                        heartCount = store.heartCount,
                        isIconUsed = (type == "like"),
                        isIconSelected = it,
                        eidtSelected = {
                            updateStoreSeleted(
                                index,
                                !store.isLiked
                            )
                        }
                    )
                    if (index != storeItems.lastIndex) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 1.dp), thickness = 1.dp, color = Gray200)
                    }                }
            }
        }
    }
}

@Preview
@Composable
fun MyStoreScreenPreview() {
    HankkijogboTheme {
        MyStoreScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            type = "like",
            storeItems = persistentListOf(StoreEntity("", 0, 0, "", true, 0, "").toMyStoreModel()),
            updateStoreSeleted = { _, _ -> }
        )
    }
}
