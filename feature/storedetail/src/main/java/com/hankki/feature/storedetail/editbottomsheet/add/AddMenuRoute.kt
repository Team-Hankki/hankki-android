package com.hankki.feature.storedetail.editbottomsheet.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.textfield.HankkiMenuTextField
import com.hankki.core.designsystem.component.textfield.HankkiPriceTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.StoreDetailSideEffect
import com.hankki.feature.storedetail.StoreDetailViewModel

@Composable
fun AddMenuRoute(
    storeId: Long,
    onNavigateUp: () -> Unit,
    onNavigateToSuccess: (Long) -> Unit,
    viewModel: StoreDetailViewModel = hiltViewModel()
) {
    val state by viewModel.storeState.collectAsStateWithLifecycle()
    val sideEffectFlow = viewModel.sideEffects

    LaunchedEffect(Unit) {
        viewModel.setStoreId(storeId)
    }

    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is StoreDetailSideEffect.MenuAddSuccess -> {
                    onNavigateToSuccess(sideEffect.storeId)
                }
                is StoreDetailSideEffect.MenuAddFailure -> {
                }
                else -> {
                }
            }
        }
    }


    AddMenuScreen(
        menuList = state.menuList,
        buttonEnabled = state.buttonEnabled,
        onNavigateUp = onNavigateUp,
        onMenuNameChange = viewModel::changeMenuName,
        onPriceChange = viewModel::changePrice,
        onDeleteMenu = viewModel::deleteMenu,
        onAddMenu = viewModel::addMenu,
        onSubmit = viewModel::submitMenus
    )
}

@Composable
private fun AddMenuScreen(
    menuList: List<MenuUiState>,
    buttonEnabled: Boolean,
    onNavigateUp: () -> Unit,
    onMenuNameChange: (Int, String) -> Unit,
    onPriceChange: (Int, String) -> Unit,
    onDeleteMenu: (Int) -> Unit,
    onAddMenu: () -> Unit,
    onSubmit: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(onNavigateUp)
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 22.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "새로운 메뉴를\n추가할게요",
                style = HankkiTheme.typography.suitH2,
                color = Gray850
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                menuList.forEachIndexed { index, menu ->
                    MenuWithPriceInputComponent(
                        name = menu.name,
                        price = menu.price,
                        isPriceError = menu.isPriceError,
                        onMenuChange = { onMenuNameChange(index, it) },
                        onPriceChange = { onPriceChange(index, it) },
                        deleteMenu = { onDeleteMenu(index) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
                AddMenuButton(onClick = onAddMenu)
            }

            Spacer(modifier = Modifier.height(12.dp))
            HankkiButton(
                text = "${menuList.size}개 추가하기",
                onClick = onSubmit,
                enabled = buttonEnabled,
                modifier = Modifier.fillMaxWidth(),
                textStyle = HankkiTheme.typography.sub3
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MenuWithPriceInputComponent(
    name: String,
    price: String,
    isPriceError: Boolean,
    onMenuChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    deleteMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.weight(1f),
        ) {
            HankkiMenuTextField(
                value = name,
                onTextChanged = onMenuChange,
                modifier = Modifier.fillMaxWidth(0.55f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            HankkiPriceTextField(
                value = price,
                onTextChanged = onPriceChange,
                isError = isPriceError,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(3.dp))

        Column {
            Text(
                text = "",
                style = HankkiTheme.typography.body8,
                color = Color.Transparent
            )

            Spacer(modifier = Modifier.height(11.dp))

            Icon(
                painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_circle_x),
                contentDescription = "delete",
                tint = Gray300,
                modifier = Modifier
                    .size(32.dp)
                    .noRippleClickable(onClick = deleteMenu)
            )
        }
    }
}

@Composable
fun AddMenuButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier.noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_add_circle_dark_plus),
            contentDescription = "plus",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "메뉴추가하기",
            style = HankkiTheme.typography.body3,
            color = Gray400
        )
    }
}


data class MenuUiState(
    val name: String = "",
    val price: String = "",
    val isPriceError: Boolean = false
)