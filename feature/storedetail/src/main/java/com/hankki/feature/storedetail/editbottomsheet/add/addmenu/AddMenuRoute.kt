package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.textfield.HankkiMenuTextField
import com.hankki.core.designsystem.component.textfield.HankkiPriceTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.*
import kotlinx.collections.immutable.PersistentList

@Composable
fun AddMenuRoute(
    storeId: Long,
    onNavigateUp: () -> Unit,
    onNavigateToSuccess: (Int) -> Unit,
    viewModel: AddMenuViewModel = hiltViewModel()
) {
    val state by viewModel.addMenuState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setStoreId(storeId)
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                AddMenuSideEffect.NavigateToSuccess -> onNavigateToSuccess(state.menuList.size)
                AddMenuSideEffect.NavigateBack -> onNavigateUp()
                is AddMenuSideEffect.ShowError -> {
                    // Handle error state
                }
            }
        }
    }

    AddMenuScreen(
        menuList = state.menuList,
        buttonEnabled = state.buttonEnabled,
        onNavigateUp = { viewModel.onEvent(AddMenuEvent.OnBackClick) },
        onMenuNameChange = { index, name -> viewModel.onEvent(AddMenuEvent.OnMenuNameChange(index, name)) },
        onPriceChange = { index, price -> viewModel.onEvent(AddMenuEvent.OnPriceChange(index, price)) },
        onDeleteMenu = { index -> viewModel.onEvent(AddMenuEvent.OnDeleteMenu(index)) },
        onAddMenu = { viewModel.onEvent(AddMenuEvent.OnAddMenu) },
        onSubmit = { viewModel.onEvent(AddMenuEvent.OnSubmit) }
    )
}

@Composable
private fun AddMenuScreen(
    menuList: PersistentList<MenuUiState>,
    buttonEnabled: Boolean,
    onNavigateUp: () -> Unit,
    onMenuNameChange: (Int, String) -> Unit,
    onPriceChange: (Int, String) -> Unit,
    onDeleteMenu: (Int) -> Unit,
    onAddMenu: () -> Unit,
    onSubmit: () -> Unit
) {
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

            Spacer(modifier = Modifier.height(34.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(
                    count = menuList.size,
                    key = { index -> index }
                ) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                        ) {
                            HankkiMenuTextField(
                                value = menuList[index].name,
                                onTextChanged = { onMenuNameChange(index, it) },
                                modifier = Modifier.fillMaxWidth(0.55f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            HankkiPriceTextField(
                                value = menuList[index].price,
                                onTextChanged = { onPriceChange(index, it) },
                                isError = menuList[index].isPriceError,
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
                                imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_circle_x),
                                contentDescription = "delete",
                                tint = Gray300,
                                modifier = Modifier
                                    .size(32.dp)
                                    .noRippleClickable { onDeleteMenu(index) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.noRippleClickable(onClick = onAddMenu),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_add_circle_dark_plus),
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
