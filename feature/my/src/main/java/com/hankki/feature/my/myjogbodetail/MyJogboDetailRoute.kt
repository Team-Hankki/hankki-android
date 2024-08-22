package com.hankki.feature.my.myjogbodetail

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val myJogboDetailState by myJogboDetailViewModel.myJogboDetailState.collectAsStateWithLifecycle()

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
        jogboTitle = myJogboDetailState.myStoreItems.title,
        jogboChips = myJogboDetailState.myStoreItems.chips.toPersistentList(),
        state = myJogboDetailState.uiState,
        deleteDialogState = myJogboDetailState.deleteDialogState,
        shareDialogState = myJogboDetailState.shareDialogState,
        userNickname = myJogboDetailState.userInformation.nickname,
        updateShareDialogState = { myJogboDetailViewModel.updateShareDialogState(myJogboDetailState.shareDialogState) },
        updateDeleteDialogState = {
            myJogboDetailViewModel.updateDeleteDialogState(
                myJogboDetailState.deleteDialogState
            )
        },
        deleteSelectedStore = { storeId ->
            myJogboDetailViewModel.deleteSelectedStore(
                favoriteId,
                storeId
            )
        },
        selectedStoreId = myJogboDetailState.selectedStoreId,
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
            .navigationBarsPadding()
            .fillMaxSize()
            .background(Red500),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .statusBarsPadding()
                .background(Red500)
        )

        HankkiTopBar(
            modifier = Modifier.background(Red500),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(44.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = Color.Unspecified
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

        when (state) {
            is EmptyUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
                }
            }

            is EmptyUiState.Success -> {
                JogboFolder(
                    title = jogboTitle,
                    chips = jogboChips,
                    userNickname = userNickname,
                    shareJogbo = updateShareDialogState
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                        Spacer(modifier = Modifier.height(20.dp))

                        MoveToHomeButton(
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .noRippleClickable(navigateToHome),
                        )
                    }
                }
            }

            is EmptyUiState.Empty -> {
                JogboFolder(
                    title = jogboTitle,
                    chips = jogboChips,
                    userNickname = userNickname,
                    shareJogbo = updateShareDialogState
                )

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

            is EmptyUiState.Failure -> {}
        }
    }
}

@Preview
@Composable
fun MyJogboDetailScreenPreview() {
    HankkijogboTheme {
    }
}
