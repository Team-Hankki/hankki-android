package com.hankki.feature.my.myjogbodetail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
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
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
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
import com.hankki.feature.my.component.MyStoreItem
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
    navigateToNewJogbo: () -> Unit, // TODO: 받아와야함
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
        navigateToHome = myJogboDetailViewModel::navigateToHome,
        navigateToNewJogbo = navigateToNewJogbo,
        shareJogbo = myJogboDetailViewModel::shareJogbo
    )
}

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
    navigateToNewJogbo: () -> Unit,
    isSharedJogbo: Boolean = true, // TODO: 공유받은 족보라면 true로 바꿔줘야함
    shareJogbo: (Context, String, String, String) -> Unit // TODO: 족보 공유
) {
    val configuration = LocalConfiguration.current
    val height by rememberSaveable {
        mutableDoubleStateOf(configuration.screenHeightDp * 0.09)
    }
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    if (shareDialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.no_jogbo),
            negativeButtonTitle = stringResource(R.string.go_back),
            positiveButtonTitle = stringResource(id = R.string.look_around),
            onNegativeButtonClicked = updateShareDialogState,
            onPositiveButtonClicked = navigateToHome
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
                    text = if (isSharedJogbo) stringResource(R.string.shared_jogbo) else stringResource(
                        R.string.my_jogbo
                    ),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .navigationBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
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
                                shareJogboDialogState = {
                                    state.data.firstOrNull { it.imageUrl != null }?.imageUrl?.let {
                                        shareJogbo(
                                            context,
                                            it, // TODO: 문제점 -> 만약 식당이 전부 이미지가 없으면 공유가 안됨.
                                            jogboTitle,
                                            userNickname
                                        )
                                    }
                                },
                                // isSharedJogbo = isSharedJogbo
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        items(state.data) { store ->
                            MyStoreItem(
                                storeId = store.id,
                                storeImageUrl = store.imageUrl,
                                category = store.category,
                                storeName = store.name,
                                price = store.lowestPrice,
                                heartCount = store.heartCount,
                                isIconUsed = false,
                                isIconSelected = false,
                                onClickItem = { if (!isSharedJogbo) navigateToStoreDetail(store.id) },
                                onLongClickItem = {
                                    if (!isSharedJogbo) {
                                        updateSelectedStoreId(store.id)
                                        updateDeleteDialogState()
                                    }
                                }
                            )
                            if (state.data.indexOf(store) != state.data.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(
                                        vertical = 1.dp,
                                        horizontal = 22.dp
                                    ),
                                    thickness = 1.dp,
                                    color = Gray200
                                )
                            }
                        }

                        if (!isSharedJogbo) item {
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
                                shareJogboDialogState = updateShareDialogState,
                                //isSharedJogbo = isSharedJogbo
                            )

                            Spacer(modifier = Modifier.height((height).dp))

                            if (!isSharedJogbo)
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

            if (isSharedJogbo)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    BottomBlurLayout()

                    Column {
                        HankkiButton(
                            modifier = Modifier
                                .padding(horizontal = 22.dp)
                                .fillMaxWidth()
                                .padding(bottom = 15.dp),
                            text = stringResource(R.string.add_to_my_jogbo),
                            onClick = navigateToNewJogbo, // TODO: 족보 생성하기 페이지로 이동
                            enabled = true,
                            textStyle = HankkiTheme.typography.sub3,
                        )
                    }
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
            navigateToHome = {},
            navigateToNewJogbo = {}
        )
    }
}
