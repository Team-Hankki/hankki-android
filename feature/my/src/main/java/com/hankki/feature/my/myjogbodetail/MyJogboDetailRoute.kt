package com.hankki.feature.my.myjogbodetail

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.BuildConfig.KAKAO_SHARE_DEFAULT_IMAGE
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.amplitude.PropertyKey
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.layout.EmptyViewWithButton
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.response.Store
import com.hankki.feature.my.R
import com.hankki.feature.my.component.MyStoreItem
import com.hankki.feature.my.myjogbodetail.component.JogboHeader
import com.hankki.feature.my.myjogbodetail.component.MoveToHomeButton
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MyJogboDetailRoute(
    favoriteId: Long,
    navigateToDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    navigateToNewSharedJogbo: (Boolean, Long) -> Unit,
    navigateToMyJogbo: (Boolean) -> Unit,
    navigateToLogin: (Boolean) -> Unit,
    isSharedJogbo: Boolean = false,
    myJogboDetailViewModel: MyJogboDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by myJogboDetailViewModel.myJogboDetailState.collectAsStateWithLifecycle()

    LaunchedEffect(isSharedJogbo) {
        if (isSharedJogbo)
            myJogboDetailViewModel.getSharedJogboDetail(favoriteId)
        else {
            myJogboDetailViewModel.getSharedJogboDetail(favoriteId)
            myJogboDetailViewModel.getJogboDetail(favoriteId)
        }
    }

    LaunchedEffect(myJogboDetailViewModel.mySideEffect, lifecycleOwner) {
        myJogboDetailViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyJogboDetailSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                    is MyJogboDetailSideEffect.NavigateToHome -> navigateToHome()
                    is MyJogboDetailSideEffect.NavigateToMyJogbo -> navigateToMyJogbo(true)
                    is MyJogboDetailSideEffect.ShowLoginDialog -> myJogboDetailViewModel.updateLoginDialog()
                    is MyJogboDetailSideEffect.NavigateToLogin -> navigateToLogin(true)
                }
            }
    }

    MyJogboDetailScreen(
        jogboTitle = state.myStoreItems.title,
        jogboChips = state.myStoreItems.chips.toPersistentList(),
        state = state.uiState,
        deleteDialogState = state.deleteDialogState,
        shareDialogState = state.shareDialogState,
        userName = state.userInformation.nickname,
        updateShareDialogState = myJogboDetailViewModel::updateShareDialogState,
        updateDeleteDialogState = myJogboDetailViewModel::updateDeleteDialogState,
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
        navigateToNewSharedJogbo = navigateToNewSharedJogbo,
        shareJogbo = myJogboDetailViewModel::shareJogbo,
        isSharedJogbo = isSharedJogbo,
        favoriteId = favoriteId,
        isJogboOwner = state.isJogboOwner,
        loginDialogState = state.loginDialogState,
        updateLoginDialogState = myJogboDetailViewModel::updateLoginDialog,
        navigateToLogin = navigateToLogin,
        navigateToMyJogbo = navigateToMyJogbo
    )

    BackHandler(onBack = { navigateToMyJogbo(false) })
}

@Composable
fun MyJogboDetailScreen(
    jogboTitle: String,
    jogboChips: PersistentList<String>,
    state: EmptyUiState<PersistentList<Store>>,
    deleteDialogState: Boolean,
    shareDialogState: Boolean,
    userName: String,
    updateShareDialogState: () -> Unit,
    updateDeleteDialogState: () -> Unit,
    deleteSelectedStore: (Long) -> Unit,
    selectedStoreId: Long,
    updateSelectedStoreId: (Long) -> Unit,
    navigateToStoreDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    navigateToNewSharedJogbo: (Boolean, Long) -> Unit,
    isSharedJogbo: Boolean,
    shareJogbo: (Context, String, String, String, Long, () -> Unit) -> Unit,
    favoriteId: Long,
    isJogboOwner: Boolean,
    loginDialogState: Boolean,
    updateLoginDialogState: () -> Unit,
    navigateToLogin: (Boolean) -> Unit,
    navigateToMyJogbo: (Boolean) -> Unit
) {
    val tracker = LocalTracker.current
    val configuration = LocalConfiguration.current

    val height by rememberSaveable {
        mutableDoubleStateOf(configuration.screenHeightDp * 0.09)
    }
    val scrollState = rememberLazyListState()
    val context = LocalContext.current

    if (isSharedJogbo && !isJogboOwner) {
        tracker.track(
            type = EventType.NONE,
            name = "Shared_Jokbo_Page",
            properties = mapOf(
                PropertyKey.JOGBO to jogboTitle
            )
        )
    }

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

    if (loginDialogState) {
        SingleButtonDialog(
            title = stringResource(R.string.need_login),
            buttonTitle = stringResource(R.string.login),
            onConfirmation = {
                updateLoginDialogState()
                navigateToLogin(false)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
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
                        JogboHeader(
                            isSharedJogbo = isSharedJogbo,
                            isJogboOwner = isJogboOwner,
                            jogboTitle = jogboTitle,
                            jogboChips = jogboChips,
                            userName = userName,
                            navigateToMyJogbo = navigateToMyJogbo,
                            onClickShareButton = {
                                val defaultImageUrl = KAKAO_SHARE_DEFAULT_IMAGE
                                val imageUrl =
                                    state.data.firstOrNull { it.imageUrl != null }?.imageUrl
                                        ?: defaultImageUrl

                                shareJogbo(
                                    context,
                                    imageUrl,
                                    jogboTitle,
                                    userName,
                                    favoriteId,
                                    {
                                        tracker.track(
                                            type = EventType.COMPLETED,
                                            name = "Mypage_MyJokbo_Share",
                                            properties = mapOf(
                                                PropertyKey.JOGBO to jogboTitle
                                            )
                                        )
                                    }
                                )

                                tracker.track(
                                    type = EventType.NONE,
                                    name = "Mypage_MyJokbo_Share",
                                    properties = mapOf(
                                        PropertyKey.JOGBO to jogboTitle
                                    )
                                )
                            }
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
                            onClickItem = {
                                if (!isSharedJogbo || isJogboOwner) navigateToStoreDetail(store.id)
                            },
                            onLongClickItem = {
                                if (!isSharedJogbo) {
                                    updateSelectedStoreId(store.id)
                                    updateDeleteDialogState()
                                }
                            }
                        )
                    }

                    if ((isSharedJogbo && isJogboOwner) || !isSharedJogbo)
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
                        JogboHeader(
                            isSharedJogbo = isSharedJogbo,
                            isJogboOwner = isJogboOwner,
                            jogboTitle = jogboTitle,
                            jogboChips = jogboChips,
                            userName = userName,
                            navigateToMyJogbo = navigateToMyJogbo,
                            onClickShareButton = updateShareDialogState
                        )

                        Spacer(modifier = Modifier.height((height).dp))
                    }

                    item {
                        if (isJogboOwner)
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

        if (!isJogboOwner)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomBlurLayout(
                    imageBlur = com.hankki.core.designsystem.R.drawable.img_white_gradient_bottom_middle
                )

                Column {
                    HankkiButton(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(horizontal = 22.dp)
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        text = stringResource(R.string.add_to_my_jogbo),
                        onClick = {
                            navigateToNewSharedJogbo(isSharedJogbo, favoriteId)
                            tracker.track(
                                type = EventType.ADD,
                                name = "Shared_Jokbo_MyJokbo",
                                properties = mapOf(
                                    PropertyKey.JOGBO to jogboTitle
                                )
                            )
                        },
                        enabled = true,
                        textStyle = HankkiTheme.typography.sub3,
                    )
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
            navigateToDetail = {},
            navigateToHome = {},
            navigateToNewSharedJogbo = { _, _ -> },
            navigateToMyJogbo = {},
            isSharedJogbo = false,
            navigateToLogin = {},
        )
    }
}
