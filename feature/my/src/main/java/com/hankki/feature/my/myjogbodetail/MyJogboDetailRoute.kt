package com.hankki.feature.my.myjogbodetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.hankki.core.designsystem.component.layout.CircleLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.response.Store
import com.hankki.domain.my.entity.response.UserInformationEntity
import com.hankki.feature.my.R
import com.hankki.feature.my.component.EmptyStoreView
import com.hankki.feature.my.component.JogboFolder
import com.hankki.feature.my.component.StoreItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MyJogboDetailRoute(
    favoriteId: Long,
    paddingValues: PaddingValues,
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
                    is MyJogboSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                }
            }
    }

    MyJogboDetailScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        jogboTitle = myJogboDetailState.myStoreItems.title,
        jogboChips = myJogboDetailState.myStoreItems.chips.toPersistentList(),
        storeItems = myJogboDetailState.storesUiState,
        deleteDialogState = myJogboDetailState.showDeleteDialog,
        shareDialogState = myJogboDetailState.showShareDialog,
        userInformation = myJogboDetailState.userInformation,
        updateShareDialogState = { myJogboDetailViewModel.updateShareDialog(myJogboDetailState.showShareDialog) },
        updateDeleteDialogState = { myJogboDetailViewModel.updateDeleteDialog(myJogboDetailState.showDeleteDialog) },
        deleteJogboStore = { storeId ->
            myJogboDetailViewModel.deleteJogboStore(
                favoriteId,
                storeId
            )
        },
        selectedStoreId = myJogboDetailState.selectedStoreId,
        updateSelectedStoreId = { storeId -> myJogboDetailViewModel.updateSelectedStoreId(storeId) },
        onClickStoreItem = { storeId -> myJogboDetailViewModel.onClickStoreItem(storeId) },
        userName = myJogboDetailState.userInformation.nickname,
        navigateToHome = navigateToHome
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyJogboDetailScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    jogboTitle: String,
    jogboChips: PersistentList<String>,
    storeItems: EmptyUiState<PersistentList<Store>>,
    deleteDialogState: Boolean,
    shareDialogState: Boolean,
    userInformation: UserInformationEntity,
    updateShareDialogState: () -> Unit,
    updateDeleteDialogState: () -> Unit,
    deleteJogboStore: (Long) -> Unit,
    selectedStoreId: Long,
    updateSelectedStoreId: (Long) -> Unit,
    onClickStoreItem: (Long) -> Unit,
    userName: String,
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
            title = stringResource(R.string.delete_store),
            negativeButtonTitle = stringResource(R.string.go_back),
            positiveButtonTitle = stringResource(id = R.string.delete),
            onNegativeButtonClicked = updateDeleteDialogState,
            onPositiveButtonClicked = {
                deleteJogboStore(selectedStoreId)
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize()
            .background(Red),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .statusBarsPadding()
                .background(Red)
        )
        HankkiTopBar(
            modifier = Modifier.background(Red),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = Color.Unspecified
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.my_store_jogbo),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )

        Spacer(
            modifier = Modifier
                .background(Red)
                .height(4.dp)
        )

        JogboFolder(
            title = jogboTitle,
            chips = jogboChips,
            userName = userInformation.nickname,
            shareJogbo = updateShareDialogState
        )

        when (storeItems) {
            is EmptyUiState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                ){
                    CircleLoadingScreen()
                }
            }

            is EmptyUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                        .padding(horizontal = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    items(storeItems .data) { store ->
                        StoreItem(
                            imageUrl = store.imageUrl,
                            category = store.category,
                            name = store.name,
                            price = store.lowestPrice,
                            heartCount = store.heartCount,
                            isIconUsed = false,
                            isIconSelected = false,
                            modifier = Modifier.combinedClickable(
                                onClick = { onClickStoreItem(store.id) },
                                onLongClick = {
                                    updateSelectedStoreId(store.id)
                                    updateDeleteDialogState()
                                }
                            )
                        )
                        if (storeItems.data.indexOf(store) != storeItems.data.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 1.dp),
                                thickness = 1.dp,
                                color = Gray200
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .padding(bottom = 30.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .wrapContentSize()
                                .background(Gray100)
                                .padding(12.dp)
                                .padding(end = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "add",
                                modifier = Modifier.size(24.dp),
                                tint = Gray500
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = stringResource(R.string.go_to_store),
                                color = Gray500,
                                style = HankkiTheme.typography.body6,
                                modifier = Modifier.noRippleClickable(navigateToHome)
                            )
                        }
                    }
                }
            }

            is EmptyUiState.Empty -> {
                EmptyStoreView(
                    text = "나의 족보에\n" +
                            "식당을 추가해보세요"
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
