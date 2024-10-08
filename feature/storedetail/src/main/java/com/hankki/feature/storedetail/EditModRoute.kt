package com.hankki.feature.storedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.feature.storedetail.component.HankkiModMenuField
import com.hankki.feature.storedetail.component.HankkiModPriceField

@Composable
fun EditModRoute(
    storeId: Long,
    menuId: Long,
    menuName: String,
    price: String,
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onMenuUpdated: (String, String) -> Unit
) {
    var updatedMenuName by remember { mutableStateOf(menuName) }
    var updatedPrice by remember { mutableStateOf(price) }
    var isPriceValid by remember { mutableStateOf(true) }
    var isMenuFocused by remember { mutableStateOf(false) }

    ModifyMenuScreen(
        menuName = menuName,
        updatedMenuName = updatedMenuName,
        price = updatedPrice,
        isMenuFocused = isMenuFocused,
        onMenuNameChanged = { updatedMenuName = it },
        onPriceChanged = { newPrice ->
            updatedPrice = newPrice
            isPriceValid = newPrice.toIntOrNull()?.let { it < 8000 } ?: false
        },
        isPriceValid = isPriceValid,
        onNavigateUp = onNavigateUp,
        onSubmit = {
            if (isPriceValid) {
                viewModel.updateMenu(storeId, menuId, updatedMenuName, updatedPrice.toInt())
                onMenuUpdated(updatedMenuName, updatedPrice)
                onNavigateUp()
            }
        },
        onMenuFocused = { isFocused -> isMenuFocused = isFocused }
    )
}

@Composable
fun ModifyMenuScreen(
    menuName: String,
    updatedMenuName: String,
    price: String,
    isMenuFocused: Boolean,
    onMenuNameChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    isPriceValid: Boolean,
    onNavigateUp: () -> Unit,
    onSubmit: () -> Unit,
    onMenuFocused: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                }
            },
            clearText = { onMenuNameChanged("") },
            placeholder = "새로운 메뉴 이름",
            onMenuFocused = onMenuFocused
        )


        Spacer(modifier = Modifier.padding(top = 12.dp))
        HankkiModPriceField(
            label = "가격",
            priceValue = price,
            onPriceValueChange = onPriceChanged,
            isError = !isPriceValid,
            errorMessage = "8000원 이하만 입력 가능합니다.",
            clearText = { onPriceChanged("") }
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "모두에게 보여지는 정보이니 신중하게 수정 부탁드려요",
            style = HankkiTheme.typography.suitBody3,
            color = Gray400,
            modifier = Modifier
                .padding(start = 36.dp, end = 35.dp, bottom = 12.dp)
        )

        HankkiButton(
            text = "수정 완료",
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 15.dp)
                .navigationBarsPadding()
        )
    }
}