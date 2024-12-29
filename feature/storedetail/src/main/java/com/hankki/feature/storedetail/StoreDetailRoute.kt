package com.hankki.feature.storedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.hankki.feature.storedetail.component.StoreDetailMenuButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.ImageDoubleButtonDialog
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.event.LocalButtonSnackBarTrigger
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray350
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.feature.storedetail.component.StoreDetailDifferentButton
import com.hankki.feature.storedetail.component.StoreDetailHeadBox
import com.hankki.feature.storedetail.component.StoreDetailMenuBox
import com.hankki.feature.storedetail.editbottomsheet.EditMenuBottomSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun StoreDetailRoute(
    storeId: Long,
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    navigateToStoreDetailReportRoute: (Long) -> Unit,
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onAddMenuClick: (Long) -> Unit,
    onEditMenuClick: (Long) -> Unit
) {
    val tracker = LocalTracker.current
    val snackBar = LocalSnackBarTrigger.current
    val buttonSnackBar = LocalButtonSnackBarTrigger.current

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
                categoryImageUrl = storeDetail.categoryImageUrl,
                tag = storeDetail.category,
                latitude = storeState.latitude,
                longitude = storeState.longitude,
                menuItems = storeDetail.menus.toPersistentList(),
                isLiked = storeState.isLiked,
                heartCount = storeState.heartCount,
                imageUrl = storeDetail.imageUrls.firstOrNull()
                    ?: (com.hankki.feature.storedetail.R.drawable.img_store_default).toString(),
                onNavigateUp = navigateUp,
                onShowSnackBar = buttonSnackBar,
                onLikeClicked = {
                    viewModel.toggleLike(storeId)
                    tracker.track(
                        type = EventType.CLICK,
                        name = "RestInfo_Like"
                    )
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
                onReportClicked = { navigateToStoreDetailReportRoute(storeId) },
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
    categoryImageUrl: String,
    tag: String,
    latitude: Double,
    longitude: Double,
    menuItems: PersistentList<MenuItem>,
    isLiked: Boolean,
    heartCount: Int,
    imageUrl: String?,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    onLikeClicked: () -> Unit,
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
            .background(Gray100)
            .navigationBarsPadding()
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
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .padding(start = 6.dp, top = 2.dp)
                                .noRippleClickable(onClick = onNavigateUp),
                            tint = Gray50
                        )
                    }
                )
            }
        }

        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .offset(y = (-50).dp)
                .padding(end = 15.dp)
        ) {
            StoreDetailDifferentButton(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_home),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                },
                content = {
                    Text(
                        text = stringResource(id = com.hankki.feature.storedetail.R.string.is_it_different),
                        style = HankkiTheme.typography.caption5,
                        color = Gray900
                    )
                },
                onClick = onReportClicked,
                tailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Gray350
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            StoreDetailHeadBox(
                title = title,
                categoryImageUrl = categoryImageUrl,
                tag = tag,
                likeButton = {
                    StoreDetailMenuButton(
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = if (isLiked) R.drawable.ic_read_heart else R.drawable.ic_heart),
                                contentDescription = "좋아요 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = heartCount.toString(),
                                style = HankkiTheme.typography.body8,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Gray850,
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
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_jogbo),
                                contentDescription = "추가 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = stringResource(id = R.string.add_new_jogbo),
                                style = HankkiTheme.typography.body8,
                                color = Gray850
                            )
                        },
                        onClick = {
                            openBottomSheet()
                        }
                    )
                },
                latitude = latitude,
                longitude = longitude
            )

            StoreDetailMenuBox(
                menuItems = menuItems,
                onMenuEditClick = openEditMenuBottomSheet
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
            ) {
                Text(
                    text = stringResource(id = com.hankki.feature.storedetail.R.string.store_detail_caution_title),
                    style = HankkiTheme.typography.caption3,
                    color = Gray400
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = com.hankki.feature.storedetail.R.string.store_detail_caution_content),
                    style = HankkiTheme.typography.caption2,
                    color = Gray400
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}
