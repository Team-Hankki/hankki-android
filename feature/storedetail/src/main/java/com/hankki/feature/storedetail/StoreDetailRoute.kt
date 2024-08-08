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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.feature.storedetail.component.StoreDetailMenuBox
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun StoreDetailRoute(
    storeId: Long,
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    viewModel: StoreDetailViewModel = hiltViewModel(),
) {
    val storeState by viewModel.storeState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(storeId) {
        viewModel.fetchStoreDetail(storeId)
    }

    val systemUiController = rememberSystemUiController()
    val customColor = White

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
        is UiState.Loading -> {}

        is UiState.Success -> {
            val storeDetail = state.data
            StoreDetailScreen(
                title = storeDetail.name,
                tag = storeDetail.category,
                menuItems = storeDetail.menus.toPersistentList(),
                isLiked = storeState.isLiked,
                heartCount = storeState.heartCount,
                imageUrl = storeDetail.imageUrls.firstOrNull()
                    ?: (com.hankki.feature.storedetail.R.drawable.img_default_store_detail).toString(),
                selectedIndex = storeState.selectedIndex,
                buttonLabels = storeState.buttonLabels,
                onNavigateUp = navigateUp,
                onLikeClicked = { viewModel.toggleLike(storeId) },
                onSelectIndex = viewModel::updateSelectedIndex,
                isOpenBottomSheet = storeState.isOpenBottomSheet,
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
                onAddMenuClicked = { viewModel.updateDialogState(StoreDetailDialogState.MENU_EDIT) },
                onReportClicked = {
                    viewModel.fetchNickname()
                    viewModel.updateDialogState(StoreDetailDialogState.REPORT_CONFIRMATION)
                }
            )
        }

        is UiState.Failure -> {}
    }

    when (dialogState) {
        StoreDetailDialogState.MENU_EDIT -> {
            SingleButtonDialog(
                title = "조금만 기다려주세요!",
                description = "메뉴를 편집할 수 있도록 준비하고 있어요 :)",
                buttonTitle = "확인",
                onConfirmation = { viewModel.updateDialogState(StoreDetailDialogState.CLOSED) }
            )
        }

        StoreDetailDialogState.REPORT_CONFIRMATION -> {
            DoubleButtonDialog(
                title = "정말 제보하시겠어요?",
                description = "여러분의 제보가 더 정확한 한끼족보를 만들어줘요!",
                negativeButtonTitle = "돌아가기",
                positiveButtonTitle = "제보하기",
                onNegativeButtonClicked = {
                    viewModel.updateDialogState(StoreDetailDialogState.CLOSED)
                    viewModel.resetSelectedIndex()
                },
                onPositiveButtonClicked = {
                    viewModel.updateDialogState(StoreDetailDialogState.REPORT)
                    viewModel.resetSelectedIndex()
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
                    viewModel.updateDialogState(StoreDetailDialogState.CLOSED)
                    viewModel.resetSelectedIndex()
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
    onLikeClicked: () -> Unit,
    onSelectIndex: (Int) -> Unit,
    isOpenBottomSheet: Boolean,
    openBottomSheet: () -> Unit,
    jogboItems: PersistentList<JogboResponseModel>,
    addNewJogbo: () -> Unit,
    onDismissBottomSheetRequest: () -> Unit,
    addStoreAtJogbo: (Long) -> Unit,
    onAddMenuClicked: () -> Unit,
    onReportClicked: () -> Unit,
) {
    if (isOpenBottomSheet) {
        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            addNewJogbo = addNewJogbo,
            onDismissRequest = onDismissBottomSheetRequest,
            onAddJogbo = { jogboId ->
                addStoreAtJogbo(jogboId)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Gray50)
    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = "식당 사진",
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1.5f),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(com.hankki.feature.storedetail.R.drawable.img_default_store_detail),
                error = painterResource(com.hankki.feature.storedetail.R.drawable.img_default_store_detail)
            )

            Image(
                painter = painterResource(id = R.drawable.img_black_gradation_top),
                contentDescription = "black gradient",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column {
                Spacer(modifier = Modifier.statusBarsPadding())
                HankkiTopBar(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .size(48.dp)
                                .noRippleClickable(onClick = onNavigateUp),
                            tint = Color.Unspecified
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
                                painter = painterResource(id = if (isLiked) R.drawable.ic_red_like else R.drawable.ic_like),
                                contentDescription = "좋아요 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = heartCount.toString(),
                                style = HankkiTheme.typography.sub3,
                                color = Gray500
                            )
                        },
                        onClick = {
                            onLikeClicked()
                        }
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
                                style = HankkiTheme.typography.sub3,
                                color = Gray500
                            )
                        },
                        onClick = {
                            openBottomSheet()
                        }
                    )
                },
                onMenuEditClick = onAddMenuClicked
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
                                style = HankkiTheme.typography.body3.copy(color = if (isSelected) Color.Red else Gray400),
                                modifier = Modifier.weight(1f),
                            )
                        },
                        onClick = {
                            onSelectIndex(index)
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
            }
        }
    }
}
