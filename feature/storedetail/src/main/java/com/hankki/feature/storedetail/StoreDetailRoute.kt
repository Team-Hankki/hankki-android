package com.hankki.feature.storedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreDetailMenuButton
import com.hankki.core.designsystem.component.button.StoreDetailReportButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.ImageDoubleButtonDialog
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.event.LocalSnackBarWithButtonTrigger
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.feature.storedetail.component.StoreDetailMenuBox
import com.hankki.feature.storedetail.editbottomsheet.EditMenuBottomSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun StoreDetailRoute(
    storeId: Long,
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onAddMenuClick: (Long) -> Unit,
    onEditMenuClick: (Long) -> Unit
) {
    val tracker = LocalTracker.current
    val snackBar = LocalSnackBarTrigger.current
    val buttonSnackBar = LocalSnackBarWithButtonTrigger.current

    val storeState by viewModel.storeState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    val sideEffectFlow = viewModel.sideEffects

    val systemUiController = rememberSystemUiController()
    val customColor = White

    LaunchedEffect(storeId) {
        viewModel.fetchStoreDetail(storeId)
    }

    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                StoreDetailSideEffect.NavigateUp -> navigateUp()
                StoreDetailSideEffect.NavigateToAddNewJogbo -> navigateToAddNewJogbo()
                StoreDetailSideEffect.ShowTextSnackBar -> {
                    snackBar("이미 삭제된 식당입니다 ")
                    navigateUp()
                }
            }
        }
    }

    DisposableEffect(systemUiController) {
        systemUiController.setNavigationBarColor(
            color = customColor,
            darkIcons = true
        )

        onDispose {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = true,
                transformColorForLightContent = { Gray900 }
            )
            systemUiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = true,
                navigationBarContrastEnforced = false
            )
        }
    }

    when (val state = storeState.storeDetail) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
            ) {
                HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            val storeDetail = state.data
            StoreDetailScreen(
                title = storeDetail.name,
                tag = storeDetail.category,
                menuItems = storeDetail.menus.toPersistentList(),
                isLiked = storeState.isLiked,
                heartCount = storeState.heartCount,
                imageUrl = storeDetail.imageUrls.firstOrNull()
                    ?: (com.hankki.feature.storedetail.R.drawable.img_store_default).toString(),
                selectedIndex = storeState.selectedIndex,
                buttonLabels = storeState.buttonLabels,
                onNavigateUp = navigateUp,
                onShowSnackBar = buttonSnackBar,
                onLikeClicked = {
                    viewModel.toggleLike(storeId)
                    tracker.track(
                        type = EventType.CLICK,
                        name = "RestInfo_Like"
                    )
                },
                onSelectIndex = { index ->
                    viewModel.updateSelectedIndex(index)
                },
                isOpenBottomSheet = storeState.isOpenJogboBottomSheet,
                openBottomSheet = viewModel::controlMyJogboBottomSheet,
                jogboItems = storeState.jogboItems,
                addNewJogbo = {
                    navigateToAddNewJogbo()
                    viewModel.controlMyJogboBottomSheet()
                },
                onDismissBottomSheetRequest = viewModel::controlMyJogboBottomSheet,
                addStoreAtJogbo = { jogboId ->
                    viewModel.addStoreAtJogbo(jogboId, storeId)
                },
                onReportClicked = {
                    viewModel.fetchNickname()
                    viewModel.showReportConfirmation()
                },
                isOpenEditMenuBottomSheet = storeState.isOpenEditMenuBottomSheet,
                openEditMenuBottomSheet = viewModel::controlEditMenuBottomSheet,
                onDismissEditMenuBottomSheetRequest = viewModel::controlEditMenuBottomSheet,
                onAddMenuClick = { onAddMenuClick(storeId) },
                onEditMenuClick = { onEditMenuClick(storeId) },
            )
        }

        is UiState.Failure -> {}
    }

    when (dialogState) {
        StoreDetailDialogState.REPORT_CONFIRMATION -> {
            DoubleButtonDialog(
                title = "정말 제보하시겠어요?",
                description = "제보시 식당 정보가 앱에서 사라져요",
                negativeButtonTitle = "돌아가기",
                positiveButtonTitle = "제보하기",
                onNegativeButtonClicked = {
                    viewModel.closeDialog()
                    viewModel.resetSelectedIndex()
                },
                onPositiveButtonClicked = {
                    viewModel.showThankYouDialog()
                    viewModel.resetSelectedIndex()
                    viewModel.deleteStoreDetail(storeId)
                }
            )
        }

        StoreDetailDialogState.REPORT -> {
            ImageDoubleButtonDialog(
                name = storeState.nickname,
                title = "변동사항을 알려주셔서 감사합니다 :)\n오늘도 저렴하고 든든한 식사하세요!",
                negativeButtonTitle = "",
                positiveButtonTitle = "돌아가기",
                onNegativeButtonClicked = { },
                onPositiveButtonClicked = {
                    viewModel.closeDialog()
                    viewModel.resetSelectedIndex()
                    navigateUp()
                }
            )
        }

        else -> {}
    }
}

@Composable
fun StoreDetailScreen(
    title: String,
    tag: String,
    menuItems: PersistentList<MenuItem>,
    isLiked: Boolean,
    heartCount: Int,
    imageUrl: String?,
    selectedIndex: Int,
    buttonLabels: PersistentList<String>,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    onLikeClicked: () -> Unit,
    onSelectIndex: (Int) -> Unit,
    isOpenBottomSheet: Boolean,
    openBottomSheet: () -> Unit,
    onDismissBottomSheetRequest: () -> Unit,
    isOpenEditMenuBottomSheet: Boolean,
    openEditMenuBottomSheet: () -> Unit,
    onDismissEditMenuBottomSheetRequest: () -> Unit,
    jogboItems: PersistentList<JogboResponseModel>,
    addNewJogbo: () -> Unit,
    addStoreAtJogbo: (Long) -> Unit,
    onReportClicked: () -> Unit,
    onAddMenuClick: () -> Unit,
    onEditMenuClick: () -> Unit
) {
    val localContextResource = LocalContext.current.resources

    if (isOpenBottomSheet) {
        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            addNewJogbo = addNewJogbo,
            onDismissRequest = onDismissBottomSheetRequest,
            onAddJogbo = { jogboId ->
                addStoreAtJogbo(jogboId)
                onShowSnackBar(
                    localContextResource.getString(R.string.success_add_my_jogbo),
                    jogboId
                )
            }
        )
    }

    if (isOpenEditMenuBottomSheet) {
        EditMenuBottomSheet(
            onDismissRequest = onDismissEditMenuBottomSheetRequest,
            onAddMenuClick = onAddMenuClick,
            onEditMenuClick = onEditMenuClick
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Gray50)
    ) {
        Box {
            val isDefaultImage =
                imageUrl == (com.hankki.feature.storedetail.R.drawable.img_store_default).toString()

            AsyncImage(
                model = imageUrl,
                contentDescription = "식당 사진",
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1.5f),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(com.hankki.feature.storedetail.R.drawable.img_store_default),
                error = painterResource(com.hankki.feature.storedetail.R.drawable.img_store_default)
            )

            if (!isDefaultImage) {
                Image(
                    painter = painterResource(id = com.hankki.feature.storedetail.R.drawable.img_gradient_frame),
                    contentDescription = "black gradient",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column {
                Spacer(modifier = Modifier.statusBarsPadding())
                HankkiTopBar(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .offset(x = 6.dp, y = 2.dp)
                                .noRippleClickable(onClick = onNavigateUp),
                            tint = Gray50
                        )
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .offset(y = (-50).dp)
                .padding(horizontal = 18.dp)
                .fillMaxSize()
        ) {
            StoreDetailMenuBox(
                title = title,
                tag = tag,
                menuItems = menuItems,
                likeButton = {
                    StoreDetailMenuButton(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = if (isLiked) R.drawable.ic_red_like else com.hankki.feature.storedetail.R.drawable.ic_like),
                                contentDescription = "좋아요 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = heartCount.toString(),
                                style = HankkiTheme.typography.body6,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Gray500,
                                modifier = Modifier.weight(1f, false)
                            )
                        },
                        onClick = onLikeClicked
                    )
                },
                addMyJogboButton = {
                    StoreDetailMenuButton(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_circle_dark_plus),
                                contentDescription = "추가 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = stringResource(id = R.string.add_new_jogbo),
                                style = HankkiTheme.typography.body6,
                                color = Gray500
                            )
                        },
                        onClick = {
                            openBottomSheet()
                        }
                    )
                },
                onMenuEditClick = openEditMenuBottomSheet
            )

            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = com.hankki.feature.storedetail.R.string.is_it_different),
                    style = HankkiTheme.typography.sub1,
                    color = Gray900,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))
                buttonLabels.forEachIndexed { index, label ->
                    val isSelected = selectedIndex == index
                    StoreDetailReportButton(
                        content = {
                            Text(
                                text = label,
                                style = HankkiTheme.typography.body5.copy(color = if (isSelected) Color.Red else Gray400),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, false),
                            )
                        },
                        onClick = {
                            if (selectedIndex == index) {
                                onSelectIndex(-1)
                            } else {
                                onSelectIndex(index)
                            }
                        },
                        tailingIcon = {
                            Icon(
                                painter = painterResource(id = if (isSelected) R.drawable.ic_btn_radio_check else R.drawable.ic_btn_radio_uncheck),
                                contentDescription = "라디오 아이콘",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                        },
                        isSelected = isSelected
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                HankkiButton(
                    text = "제보하기",
                    onClick = onReportClicked,
                    modifier = Modifier
                        .fillMaxWidth(0.4f),
                    textStyle = HankkiTheme.typography.sub3,
                    enabled = selectedIndex != -1
                )

                Spacer(modifier = Modifier.height(54.dp))
            }
        }
    }
}
