package com.hankki.feature.storedetail.editbottomsheet.edit.editmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.layout.TopBlurLayout
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.feature.storedetail.R
import com.hankki.feature.storedetail.component.MenuItemComponent
import com.hankki.feature.storedetail.component.SegmentedButton

@Composable
fun EditMenuRoute(
    storeId: Long,
    viewModel: EditMenuViewModel = hiltViewModel(),
    onMenuSelected: (String) -> Unit,
    onEditModClick: (Long, String, String) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToDeleteSuccess: (Long) -> Unit,
    onNavigateToDeleteSuccessLast: (Long) -> Unit,
) {
    val snackBar = LocalSnackBarTrigger.current
    val tracker = LocalTracker.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchMenuItems(storeId)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is EditMenuSideEffect.NavigateToDeleteSuccess -> {
                    onNavigateToDeleteSuccess(sideEffect.storeId)
                    viewModel.resetDeleteSuccess()
                }
                is EditMenuSideEffect.NavigateToDeleteSuccessLast -> {
                    onNavigateToDeleteSuccessLast(sideEffect.storeId)
                    viewModel.resetDeleteSuccess()
                }
                is EditMenuSideEffect.NavigateBack -> onNavigateUp()
                is EditMenuSideEffect.ShowSnackbar -> {
                    snackBar(sideEffect.message)
                }
            }
        }
    }

    when (dialogState) {
        EditMenuDialogState.DELETE -> {
            DoubleButtonDialog(
                title = "삭제하시면 되돌릴 수 없어요\n그래도 삭제하시겠어요?",
                negativeButtonTitle = "취소",
                positiveButtonTitle = "삭제하기",
                onNegativeButtonClicked = {
                    viewModel.closeDialog()
                },
                onPositiveButtonClicked = {
                    viewModel.deleteMenuItem(storeId)
                    viewModel.closeDialog()
                    tracker.track(
                        type = EventType.CLICK,
                        name = "RestInfo_DeleteCompleted"
                    )
                }
            )
        }

        EditMenuDialogState.LAST_MENU_DELETE -> {
            DoubleButtonDialog(
                title = "메뉴가 1개 있어요\n메뉴를 삭제하면 식당이 삭제돼요",
                negativeButtonTitle = "돌아가기",
                positiveButtonTitle = "식당 삭제",
                onNegativeButtonClicked = {
                    viewModel.closeDialog()
                },
                onPositiveButtonClicked = {
                    viewModel.deleteMenuItem(storeId)
                    viewModel.closeDialog()
                    tracker.track(
                        type = EventType.CLICK,
                        name = "RestInfo_DeleteCompleted"
                    )
                }
            )
        }

        else -> {}
    }

    EditMenuScreen(
        state = uiState,
        onMenuSelected = { menuItem ->
            viewModel.selectMenuItem(menuItem)
            onMenuSelected(menuItem.name)
        },
        onDeleteMenuClick = { viewModel.showDeleteDialog() },
        onEditModClick = onEditModClick,
        onNavigateUp = onNavigateUp,
    )
}

@Composable
fun EditMenuScreen(
    state: EditMenuState,
    onMenuSelected: (MenuItem) -> Unit,
    onDeleteMenuClick: () -> Unit,
    onEditModClick: (Long, String, String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .noRippleClickable { focusManager.clearFocus() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .noRippleClickable { focusManager.clearFocus() },
        ) {
            HankkiTopBar(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                        contentDescription = "뒤로가기",
                        modifier = Modifier
                            .offset(x = 6.dp, y = 2.dp)
                            .noRippleClickable(onClick = onNavigateUp),
                        tint = Gray700
                    )
                }
            )

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        HankkiLoadingScreen()
                    }
                }
                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = state.error,
                            color = Gray900,
                            style = HankkiTheme.typography.body2
                        )
                    }
                }
                else -> EditMenuContent(
                    state = state,
                    onMenuSelected = onMenuSelected,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomBlurLayout(
                imageBlur = R.drawable.edit_blur,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
            ) {
                SegmentedButton(
                    option1 = "삭제하기",
                    option2 = "수정하기",
                    enabled = state.selectedMenuItem != null,
                    onOptionSelected = { selectedOption ->
                        state.selectedMenuItem?.let { menu ->
                            if (selectedOption == "삭제하기") {
                                onDeleteMenuClick()
                            } else {
                                onEditModClick(menu.id, menu.name, menu.price.toString())
                            }
                        }
                    },
                    modifier = Modifier
                        .height(58.dp)
                )
            }
        }
    }
}


@Composable
private fun EditMenuContent(
    state: EditMenuState,
    onMenuSelected: (MenuItem) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "어떤 메뉴를 편집할까요?",
            style = HankkiTheme.typography.suitH2,
            color = Gray900,
            modifier = Modifier.padding(start = 22.dp)
        )
        Spacer(modifier = Modifier.height(34.dp))

        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(state.menuItems) { index, menuItem ->
                    MenuItemComponent(
                        menuItem = menuItem,
                        selectedMenu = state.selectedMenuItem?.name,
                        onMenuSelected = { onMenuSelected(menuItem) },
                        modifier = Modifier.then(
                            if (index == state.menuItems.size - 1) {
                                Modifier.padding(bottom = 90.dp)
                            } else {
                                Modifier
                            }
                        )
                    )
                }
            }

            TopBlurLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
            )
        }
    }
}

