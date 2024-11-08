package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.text.input.TextFieldValue
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
fun EditModRoute(
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
        onSubmit = {
            coroutineScope.launch {
                viewModel.submitMenu()
            }
        },
        onShowDeleteDialog = viewModel::showDeleteDialog
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
    onMenuNameChanged: (TextFieldValue) -> Unit,
    onPriceChanged: (TextFieldValue) -> Unit,
    onSubmit: () -> Unit,
    onShowDeleteDialog: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isVisibleIme = WindowInsets.isImeVisible
    val isSubmitEnabled = uiState.menuNameFieldValue.text.isNotBlank() &&
            uiState.priceFieldValue.text.isNotBlank() &&
            uiState.isPriceValid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
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
                value = uiState.menuNameFieldValue,
                onValueChange = onMenuNameChanged,
                placeholder = "새로운 메뉴 이름",
                isFocused = false,
                onMenuFocused = { /* Focus 상태가 변경될 때 호출 */ },
                clearText = { onMenuNameChanged(TextFieldValue("")) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            HankkiModPriceField(
                modifier = Modifier.fillMaxWidth(),
                label = "가격",
                value = uiState.priceFieldValue,
                onValueChange = onPriceChanged,
                isError = !uiState.isPriceValid,
                errorMessage = "가격은 8000원 이하만 제보가능해요",
                isFocused = false,
                onPriceFocused = { /* Focus 상태가 변경될 때 호출 */ },
                clearText = { onPriceChanged(TextFieldValue("")) }
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isVisibleIme) {
                if (uiState.menuNameFieldValue.text != menuName && uiState.showRestoreMenuNameButton) {
                    RollbackButton(
                        text = "기존 메뉴이름 입력",
                        onClick = { onMenuNameChanged(TextFieldValue(menuName)) }
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }

                if (uiState.priceFieldValue.text != price && uiState.showRestorePriceButton) {
                    RollbackButton(
                        text = "기존 메뉴가격 입력",
                        onClick = { onPriceChanged(TextFieldValue(price)) }
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                }

                if (uiState.isOverPriceLimit) {
                    PriceWarningMessage(
                        onDeleteClick = onShowDeleteDialog
                    )
                }

                HankkiExpandedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    text = "적용",
                    onClick = {
                        if (isSubmitEnabled) {
                            focusManager.clearFocus()
                            onSubmit()
                        }
                    },
                    enabled = isSubmitEnabled,
                    textStyle = HankkiTheme.typography.sub3,
                    backgroundColor = if (isSubmitEnabled) Red500 else Red400
                )
            } else {
                Text(
                    text = "모두에게 보여지는 정보이니 신중하게 수정 부탁드려요",
                    style = HankkiTheme.typography.suitBody3,
                    color = Gray400,
                    modifier = Modifier
                        .padding(start = 36.dp, end = 35.dp, bottom = 12.dp)
                )

                HankkiMediumButton(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(bottom = 15.dp),
                    text = "수정 완료",
                    onClick = onSubmit,
                    enabled = isSubmitEnabled,
                    textStyle = HankkiTheme.typography.sub3,
                    backgroundColor = if (isSubmitEnabled) Red500 else Red400
                )
            }
        }
    }
}
