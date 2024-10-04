package com.hankki.feature.my.myjogbodetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
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
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.layout.EmptyViewWithButton
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.response.Store
import com.hankki.feature.my.R
import com.hankki.feature.my.component.StoreItem
import com.hankki.feature.my.myjogbodetail.component.JogboFolder
import com.hankki.feature.my.myjogbodetail.component.MoveToHomeButton
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MyJogboDetailRoute(
    favoriteId: Long,
    navigateUp: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    myJogboDetailViewModel: MyJogboDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by myJogboDetailViewModel.myJogboDetailState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myJogboDetailViewModel.getJogboDetail(favoriteId)
        myJogboDetailViewModel.getUserName()
    }

    LaunchedEffect(myJogboDetailViewModel.mySideEffect, lifecycleOwner) {
        myJogboDetailViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyJogboDetailSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                    is MyJogboDetailSideEffect.NavigateToHome -> navigateToHome()
                }
            }
    }

    MyJogboDetailScreen(
        navigateUp = navigateUp,
        jogboTitle = state.myStoreItems.title,
        jogboChips = state.myStoreItems.chips.toPersistentList(),
        state = state.uiState,
        deleteDialogState = state.deleteDialogState,
        shareDialogState = state.shareDialogState,
        userNickname = state.userInformation.nickname,
        updateShareDialogState = { myJogboDetailViewModel.updateShareDialogState(state.shareDialogState) },
        updateDeleteDialogState = { myJogboDetailViewModel.updateDeleteDialogState(state.deleteDialogState) },
        deleteSelectedStore = { storeId ->
            myJogboDetailViewModel.deleteSelectedStore(
                favoriteId,
                storeId
            )
        },
        selectedStoreId = state.selectedStoreId,
        updateSelectedStoreId = myJogboDetailViewModel::updateSelectedStoreId,
        navigateToStoreDetail = myJogboDetailViewModel::navigateToStoreDetail,
        navigateToHome = myJogboDetailViewModel::navigateToHome
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyJogboDetailScreen(
    navigateUp: () -> Unit,
    jogboTitle: String,
    jogboChips: PersistentList<String>,
    state: EmptyUiState<PersistentList<Store>>,
    deleteDialogState: Boolean,
    shareDialogState: Boolean,
    userNickname: String,
    updateShareDialogState: () -> Unit,
    updateDeleteDialogState: () -> Unit,
    deleteSelectedStore: (Long) -> Unit,
    selectedStoreId: Long,
    updateSelectedStoreId: (Long) -> Unit,
    navigateToStoreDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val height by rememberSaveable {
        mutableDoubleStateOf(configuration.screenHeightDp * 0.09)
    }
    val scrollState = rememberLazyListState()

    if (shareDialogState) {
        SingleButtonDialog(
            title = stringResource(R.string.please_wait),
            description = stringResource(R.string.preparing_share_jogbo),
            buttonTitle = stringResource(R.string.check),
            onConfirmation = updateShareDialogState
        )
    }

    if (deleteDialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.ask_delete_store),
            negativeButtonTitle = stringResource(R.string.go_back),
            positiveButtonTitle = stringResource(id = R.string.do_delete),
            onNegativeButtonClicked = updateDeleteDialogState,
            onPositiveButtonClicked = {
                deleteSelectedStore(selectedStoreId)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Red500)
    ) {
        HankkiTopBar(
            modifier = Modifier
                .background(Red500)
                .statusBarsPadding(),
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
                    text = stringResource(R.string.my_jogbo),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .background(White)
                .navigationBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scrollState
        ) {
            when (state) {
                is EmptyUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                        ) {
                            HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }

                is EmptyUiState.Success -> {
                    item {
                        JogboFolder(
                            title = jogboTitle,
                            chips = jogboChips,
                            userNickname = userNickname,
                            shareJogboDialogState = updateShareDialogState
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    items(state.data) { store ->
                        StoreItem(
                            imageUrl = store.imageUrl,
                            category = store.category,
                            name = store.name,
                            price = store.lowestPrice.toString(),
                            heartCount = store.heartCount,
                            isIconUsed = false,
                            isIconSelected = false,
                            modifier = Modifier.combinedClickable(
                                onClick = { navigateToStoreDetail(store.id) },
                                onLongClick = {
                                    updateSelectedStoreId(store.id)
                                    updateDeleteDialogState()
                                }
                            )
                        )
                        if (state.data.indexOf(store) != state.data.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 1.dp, horizontal = 22.dp),
                                thickness = 1.dp,
                                color = Gray200
                            )
                        }
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp, bottom = 30.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            MoveToHomeButton(
                                modifier = Modifier
                                    .noRippleClickable(navigateToHome),
                            )
                        }
                    }
                }

                is EmptyUiState.Empty -> {
                    item {
                        JogboFolder(
                            title = jogboTitle,
                            chips = jogboChips,
                            userNickname = userNickname,
                            shareJogboDialogState = updateShareDialogState
                        )

                        Spacer(modifier = Modifier.height((height).dp))

                        EmptyViewWithButton(
                            text = stringResource(R.string.my_jogbo) +
                                    stringResource(R.string.add_store_to_jogbo),
                            content = {
                                MoveToHomeButton(
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .noRippleClickable(navigateToHome),
                                )
                            }
                        )
                    }
                }

                is EmptyUiState.Failure -> {}
            }
        }
    }
}

@Preview
@Composable
fun MyJogboDetailScreenPreview() {
    HankkijogboTheme {
        MyJogboDetailRoute(
            favoriteId = 1,
            navigateUp = {},
            navigateToDetail = {},
            navigateToHome = {}
        )
    }
}
