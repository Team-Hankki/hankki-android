package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiExpandedButton
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red400
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.component.HankkiModMenuField
import com.hankki.feature.storedetail.component.HankkiModPriceField
import com.hankki.feature.storedetail.component.PriceWarningMessage
import com.hankki.feature.storedetail.component.RollbackButton
import kotlinx.coroutines.launch

@Composable
fun ModRoute(
    storeId: Long,
    menuId: Long,
    menuName: String,
    price: String,
    viewModel: ModViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToEditSuccess: (Long) -> Unit,
    onNavigateToDeleteSuccess: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sideEffectFlow = viewModel.sideEffect
    val coroutineScope = rememberCoroutineScope()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize(storeId, menuId, menuName, price)
    }

    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is ModSideEffect.NavigateToEditSuccess -> onNavigateToEditSuccess(sideEffect.storeId)
                is ModSideEffect.NavigateToDeleteSuccess -> onNavigateToDeleteSuccess(sideEffect.storeId)
                ModSideEffect.NavigateUp -> onNavigateUp()
                is ModSideEffect.MenuAddFailure -> {
                    // Handle error
                }
            }
        }
    }

    ModifyMenuScreen(
        uiState = uiState,
        menuName = menuName,
        price = price,
        onNavigateUp = onNavigateUp,
        onMenuNameChanged = viewModel::updateMenuName,
        onPriceChanged = viewModel::updatePrice,
        onMenuFocusChanged = viewModel::updateMenuFieldFocus,
        onPriceFocusChanged = viewModel::updatePriceFieldFocus,
        onSubmit = {
            coroutineScope.launch {
                viewModel.submitMenu()
            }
        },
        onShowDeleteDialog = viewModel::showDeleteDialog,
        onShowModCompleteDialog = viewModel::onShowModCompleteDialog
    )

    when (dialogState) {
        ModDialogState.DELETE -> {
            DoubleButtonDialog(
                title = "삭제하시면 되돌릴 수 없어요\n그래도 삭제하시겠어요?",
                negativeButtonTitle = "취소",
                positiveButtonTitle = "삭제하기",
                onNegativeButtonClicked = {
                    viewModel.closeDialog()
                },
                onPositiveButtonClicked = {
                    viewModel.deleteMenu()
                    viewModel.closeDialog()
                }
            )
        }

        ModDialogState.MOD_COMPLETE -> {
            DoubleButtonDialog(
                title = "메뉴를 모두 수정하셨나요?",
                negativeButtonTitle = "돌아가기",
                positiveButtonTitle = "수정완료",
                onNegativeButtonClicked = {
                    viewModel.closeDialog()
                },
                onPositiveButtonClicked = {
                    coroutineScope.launch {
                        viewModel.submitMenu()
                    }
                    viewModel.closeDialog()
                }
            )
        }

        else -> {}
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModifyMenuScreen(
    uiState: ModState,
    menuName: String,
    price: String,
    onNavigateUp: () -> Unit,
    onMenuNameChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onMenuFocusChanged: (Boolean) -> Unit,
    onPriceFocusChanged: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    onShowDeleteDialog: () -> Unit,
    onShowModCompleteDialog: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isVisibleIme = WindowInsets.isImeVisible

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
                .navigationBarsPadding(),
        ) {
            HankkiTopBar(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                        contentDescription = "뒤로가기",
                        modifier = Modifier
                            .offset(x = 6.dp, y = 2.dp)
                            .noRippleClickable(onClick = onNavigateUp),
                        tint = Gray700
                    )
                }
            )

            Spacer(modifier = Modifier.padding(top = 18.dp))
            Row {
                Text(
                    text = menuName,
                    style = HankkiTheme.typography.suitH2,
                    color = Red500,
                    modifier = Modifier.padding(start = 22.dp)
                )
                Text(
                    text = " 메뉴를",
                    style = HankkiTheme.typography.suitH2,
                    color = Gray900
                )
            }
            Text(
                text = "수정할게요",
                style = HankkiTheme.typography.suitH2,
                color = Gray900,
                modifier = Modifier.padding(start = 22.dp)
            )

            Spacer(modifier = Modifier.height(34.dp))
            HankkiModMenuField(
                modifier = Modifier.fillMaxWidth(),
                label = "메뉴 이름",
                value = uiState.menuName,
                onValueChange = onMenuNameChanged,
                placeholder = "새로운 메뉴 이름",
                isFocused = uiState.isMenuFieldFocused,
                onMenuFocused = { focused ->
                    if (focused) {
                        onPriceFocusChanged(false)
                    }
                    onMenuFocusChanged(focused)
                },
                clearText = {
                    onMenuNameChanged("")
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            HankkiModPriceField(
                modifier = Modifier.fillMaxWidth(),
                label = "메뉴 가격",
                value = uiState.price,
                onValueChange = onPriceChanged,
                isError = !uiState.isPriceValid,
                errorMessage = "8000원 이하만 제보 가능해요",
                isFocused = uiState.isPriceFieldFocused,
                onPriceFocused = { focused ->
                    if (focused) {
                        onMenuFocusChanged(false)
                    }
                    onPriceFocusChanged(focused)
                },
                clearText = {
                    onPriceChanged("")
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isVisibleIme) {
                if (uiState.menuName != menuName &&
                    uiState.showRestoreMenuNameButton &&
                    uiState.isMenuFieldFocused
                ) {
                    RollbackButton(
                        text = "기존 메뉴이름 입력",
                        onClick = { onMenuNameChanged(menuName) }
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }

                if (uiState.price != price &&
                    uiState.showRestorePriceButton &&
                    uiState.isPriceFieldFocused
                ) {
                    RollbackButton(
                        text = "기존 메뉴가격 입력",
                        onClick = { onPriceChanged(price) }
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }

                if (uiState.isOverPriceLimit && uiState.isPriceFieldFocused) {
                    PriceWarningMessage(
                        onDeleteClick = onShowDeleteDialog,
                        onDismissClick = { onPriceChanged("") }
                    )
                }

                HankkiExpandedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    text = "수정 완료",
                    onClick = onShowModCompleteDialog,
                    enabled = uiState.isSubmitEnabled,
                    textStyle = HankkiTheme.typography.sub3,
                    backgroundColor = if (uiState.isSubmitEnabled) Red500 else Red400
                )
            } else {
                Text(
                    text = "모두에게 보여지는 정보이니 신중하게 수정 부탁드려요",
                    style = HankkiTheme.typography.suitBody3,
                    color = Gray400,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )

                HankkiMediumButton(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(bottom = 15.dp),
                    text = "수정 완료",
                    onClick = {
                        if (uiState.isSubmitEnabled) {
                            focusManager.clearFocus()
                            onSubmit()
                        }
                    },
                    enabled = uiState.isSubmitEnabled,
                    textStyle = HankkiTheme.typography.sub3,
                    backgroundColor = if (uiState.isSubmitEnabled) Red500 else Red400
                )
            }
        }
    }
}
