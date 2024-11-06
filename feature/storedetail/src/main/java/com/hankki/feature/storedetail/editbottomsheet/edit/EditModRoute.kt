package com.hankki.feature.storedetail.editbottomsheet.edit

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiExpandedButton
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.*
import com.hankki.feature.storedetail.StoreDetailViewModel
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
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onMenuUpdated: (String, String) -> Unit,
    navigateToEditSuccess: (Long) -> Unit
) {
    var menuNameFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = menuName,
                selection = TextRange(menuName.length)
            )
        )
    }
    var priceFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = price,
                selection = TextRange(price.length)
            )
        )
    }
    var isPriceValid by remember { mutableStateOf(true) }
    var isMenuFocused by remember { mutableStateOf(false) }
    var isPriceFocused by remember { mutableStateOf(false) }
    var showRestoreMenuNameButton by remember { mutableStateOf(false) }
    var showRestorePriceButton by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    ModifyMenuScreen(
        menuName = menuName,
        menuNameFieldValue = menuNameFieldValue,
        price = price,
        priceFieldValue = priceFieldValue,
        isMenuFocused = isMenuFocused,
        isPriceFocused = isPriceFocused,
        showRestoreMenuNameButton = showRestoreMenuNameButton,
        showRestorePriceButton = showRestorePriceButton,
        onMenuNameChanged = { newValue ->
            menuNameFieldValue = TextFieldValue(
                text = newValue.text,
                selection = TextRange(newValue.text.length)
            )
            showRestoreMenuNameButton = newValue.text != menuName
        },
        onPriceChanged = { newValue ->
            priceFieldValue = TextFieldValue(
                text = newValue.text,
                selection = TextRange(newValue.text.length)
            )
            showRestorePriceButton = newValue.text != price
            isPriceValid = newValue.text.toIntOrNull()?.let { it < 8000 } ?: false
        },
        isPriceValid = isPriceValid,
        onNavigateUp = onNavigateUp,
        onSubmit = {
            if (isPriceValid) {
                coroutineScope.launch {
                    val parsedPrice = priceFieldValue.text.toIntOrNull()
                    if (parsedPrice != null && parsedPrice < 8000) {
                        viewModel.updateMenu(storeId, menuId, menuNameFieldValue.text, parsedPrice)
                        onMenuUpdated(menuNameFieldValue.text, priceFieldValue.text)
                        navigateToEditSuccess(storeId)
                    }
                }
            }
        },
        onMenuFocused = { isFocused ->
            isMenuFocused = isFocused
            if (!isFocused) showRestoreMenuNameButton = false
        },
        onPriceFocused = { isFocused ->
            isPriceFocused = isFocused
            if (!isFocused) showRestorePriceButton = false
        },
        focusManager = focusManager
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModifyMenuScreen(
    menuName: String,
    menuNameFieldValue: TextFieldValue,
    price: String,
    priceFieldValue: TextFieldValue,
    isMenuFocused: Boolean,
    isPriceFocused: Boolean,
    showRestoreMenuNameButton: Boolean,
    showRestorePriceButton: Boolean,
    onMenuNameChanged: (TextFieldValue) -> Unit,
    onPriceChanged: (TextFieldValue) -> Unit,
    isPriceValid: Boolean,
    onNavigateUp: () -> Unit,
    onSubmit: () -> Unit,
    onMenuFocused: (Boolean) -> Unit,
    onPriceFocused: (Boolean) -> Unit,
    focusManager: FocusManager
) {
    val isVisibleIme = WindowInsets.isImeVisible
    val isSubmitEnabled = menuNameFieldValue.text.isNotBlank() &&
            priceFieldValue.text.isNotBlank() &&
            isPriceValid
    val isOverPriceLimit = priceFieldValue.text.toIntOrNull()?.let { it >= 8000 } == true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(com.hankki.core.designsystem.R.drawable.ic_arrow_left),
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
            label = "메뉴 이름",
            value = menuNameFieldValue,
            isFocused = isMenuFocused,
            onValueChange = onMenuNameChanged,
            clearText = {
                onMenuNameChanged(TextFieldValue(""))
            },
            placeholder = "새로운 메뉴 이름",
            onMenuFocused = onMenuFocused
        )

        Spacer(modifier = Modifier.padding(top = 4.dp))
        HankkiModPriceField(
            label = "가격",
            value = priceFieldValue,
            isFocused = isPriceFocused,
            onValueChange = onPriceChanged,
            isError = !isPriceValid,
            errorMessage = "8000원 이하만 제보 가능해요.",
            clearText = {
                onPriceChanged(TextFieldValue(""))
            },
            onPriceFocused = onPriceFocused
        )

        Spacer(modifier = Modifier.weight(1f))
        if (isVisibleIme) {
            if (showRestoreMenuNameButton && menuNameFieldValue.text != menuName) {
                RollbackButton(
                    text = "기존 메뉴이름 입력",
                    onClick = {
                        onMenuNameChanged(TextFieldValue(
                            text = menuName,
                            selection = TextRange(menuName.length)
                        ))
                    }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
            }

            if (showRestorePriceButton &&
                priceFieldValue.text != price &&
                !isOverPriceLimit) {
                RollbackButton(
                    text = "기존 메뉴가격 입력",
                    onClick = {
                        onPriceChanged(TextFieldValue(
                            text = price,
                            selection = TextRange(price.length)
                        ))
                    }
                )
                Spacer(modifier = Modifier.padding(top = 16.dp))
            }

            if (isOverPriceLimit) {
                PriceWarningMessage(
                    onDeleteClick = {
                        //삭제 로직 구현
                        onPriceChanged(TextFieldValue(""))
                    }
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
                    }
                },
                enabled = isPriceValid,
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
                onClick = {
                    if (isSubmitEnabled) {
                        onSubmit()
                    }
                },
                enabled = isPriceValid,
                textStyle = HankkiTheme.typography.sub3,
                backgroundColor = if (isSubmitEnabled) Red500 else Red400
            )
        }
    }
}