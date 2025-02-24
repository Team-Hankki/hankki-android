package com.hankki.feature.my.mystore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.component.layout.EmptyView
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R
import com.hankki.feature.my.component.MyStoreItem
import com.hankki.feature.my.mystore.MyStoreViewModel.Companion.LIKE
import com.hankki.feature.my.mystore.model.MyStoreModel
import kotlinx.collections.immutable.PersistentList

@Composable
fun MyStoreRoute(
    type: String,
    navigateUp: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    myStoreViewModel: MyStoreViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by myStoreViewModel.myStoreState.collectAsStateWithLifecycle()

    LaunchedEffect(type) {
        if (type == LIKE) {
            myStoreViewModel.getLikedStoreList()
        } else {
            myStoreViewModel.getReportedStoreList()
        }
    }

    LaunchedEffect(myStoreViewModel.mySideEffect, lifecycleOwner) {
        myStoreViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyStoreSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                }
            }
    }

    MyStoreScreen(
        navigateUp = navigateUp,
        type = type,
        state = state.uiState,
        updateStoreSelected = myStoreViewModel::updateStoreSelected,
        navigateToStoreDetail = myStoreViewModel::navigateToStoreDetail,
    )
}

@Composable
fun MyStoreScreen(
    navigateUp: () -> Unit,
    type: String,
    state: EmptyUiState<PersistentList<MyStoreModel>>,
    modifier: Modifier = Modifier,
    updateStoreSelected: (Long, Boolean) -> Unit,
    navigateToStoreDetail: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(White)
            .fillMaxSize()
    ) {
        HankkiTopBar(
            modifier = Modifier.background(White),
            leadingIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .size(40.dp)
                        .noRippleClickable(onClick = navigateUp),
                )
            },
            content = {
                Text(
                    text = if (type == LIKE) stringResource(id = R.string.description_store_like) else stringResource(
                        id = R.string.description_store_report
                    ),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                EmptyUiState.Empty -> {
                    EmptyView(
                        text = if (type == LIKE) stringResource(R.string.no_liked_store) else stringResource(
                            R.string.no_reported_store
                        )
                    )
                }

                EmptyUiState.Failure -> {}

                EmptyUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                    ) {
                        HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is EmptyUiState.Success -> {
                    LazyColumn {
                        itemsIndexed(state.data) { index, store ->
                            MyStoreItem(
                                storeId = store.id,
                                storeImageUrl = store.imageURL,
                                category = store.category,
                                storeName = store.name,
                                price = store.lowestPrice,
                                heartCount = store.heartCount,
                                isIconUsed = (type == LIKE),
                                isIconSelected = store.isLiked ?: false,
                                onClickItem = { navigateToStoreDetail(store.id) },
                                editSelected = {
                                    updateStoreSelected(store.id, store.isLiked == true)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyStoreScreenPreview() {
    HankkijogboTheme {
        MyStoreScreen(
            navigateUp = {},
            type = LIKE,
            state = EmptyUiState.Empty,
            updateStoreSelected = { _, _ -> },
            navigateToStoreDetail = {}
        )
    }
}
