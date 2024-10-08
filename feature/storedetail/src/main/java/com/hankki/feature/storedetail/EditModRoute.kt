package com.hankki.feature.storedetail

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiExpandedButton
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red400
import com.hankki.core.designsystem.theme.Red500
import com.hankki.feature.storedetail.component.HankkiModMenuField
import com.hankki.feature.storedetail.component.HankkiModPriceField
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
    var updatedMenuName by remember { mutableStateOf(menuName) }
    var updatedPrice by remember { mutableStateOf(price) }
    var isPriceValid by remember { mutableStateOf(true) }
    var isMenuFocused by remember { mutableStateOf(false) }
    var isPriceFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    ModifyMenuScreen(
        menuName = menuName,
        updatedMenuName = updatedMenuName,
        price = updatedPrice,
        updatedPrice = updatedPrice,
        isMenuFocused = isMenuFocused,
        isPriceFocused = isPriceFocused,
        onMenuNameChanged = { updatedMenuName = it },
        onPriceChanged = { newPrice ->
            updatedPrice = newPrice
            isPriceValid = newPrice.toIntOrNull()?.let { it < 8000 } ?: false
        },
        isPriceValid = isPriceValid,
        onNavigateUp = onNavigateUp,
        onSubmit = {
            if (isPriceValid) {
                coroutineScope.launch {
                    val parsedPrice = updatedPrice.toIntOrNull()
                    if (parsedPrice != null && parsedPrice <= 8000) {
                        viewModel.updateMenu(storeId, menuId, updatedMenuName, parsedPrice)
                        onMenuUpdated(updatedMenuName, updatedPrice)
                        navigateToEditSuccess(storeId)
                    }
                }
            }
        },
        onMenuFocused = { isFocused -> isMenuFocused = isFocused },
        onPriceFocused = { isFocused -> isPriceFocused = isFocused },
        focusManager = focusManager
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModifyMenuScreen(
    menuName: String,
    updatedMenuName: String,
    price: String,
    updatedPrice: String,
    isMenuFocused: Boolean,
    isPriceFocused: Boolean,
    onMenuNameChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    isPriceValid: Boolean,
    onNavigateUp: () -> Unit,
    onSubmit: () -> Unit,
    onMenuFocused: (Boolean) -> Unit,
    onPriceFocused: (Boolean) -> Unit,
    focusManager: FocusManager
) {
    val isVisibleIme = WindowInsets.isImeVisible
    val isSubmitEnabled = updatedMenuName.isNotBlank() && updatedPrice.isNotBlank() && isPriceValid
    var showRestoreMenuNameButton by remember { mutableStateOf(false) }
    var showRestorePriceButton by remember { mutableStateOf(false) }

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
            menuValue = updatedMenuName,
            isFocused = isMenuFocused,
            onMenuValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    onMenuNameChanged("")
                } else {
                    onMenuNameChanged(newValue)
                    showRestoreMenuNameButton = true
                }
            },
            clearText = { onMenuNameChanged("") },
            placeholder = "새로운 메뉴 이름",
            onMenuFocused = { isFocused ->
                onMenuFocused(isFocused)
                if (!isFocused) {
                    showRestoreMenuNameButton = false
                }
            }
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))
        HankkiModPriceField(
            label = "가격",
            priceValue = updatedPrice,
            isFocused = isPriceFocused,
            onPriceValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    onPriceChanged("")
                } else {
                    onPriceChanged(newValue)
                    showRestorePriceButton = true
                }
            },
            isError = !isPriceValid,
            errorMessage = "8000원 이하만 입력 가능합니다.",
            clearText = { onPriceChanged("") },
            onPriceFocused = { isFocused ->
                onPriceFocused(isFocused)
                if (!isFocused) {
                    showRestorePriceButton = false
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        if (isVisibleIme) {
            if (showRestoreMenuNameButton && updatedMenuName != menuName) {
                RollbackButton(
                    text = "기존 메뉴이름 입력",
                    onClick = { onMenuNameChanged(menuName) }
                )
            }

            if (showRestorePriceButton && updatedPrice != price) {
                RollbackButton(
                    text = "기존 메뉴가격 입력",
                    onClick = { onPriceChanged(price) }
                )
            }

            Spacer(modifier = Modifier.padding(top = 16.dp))
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
