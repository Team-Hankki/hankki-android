package com.hankki.feature.storedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.feature.storedetail.component.MenuItemComponent
import com.hankki.feature.storedetail.component.SegmentedButton

@Composable
fun EditMenuRoute(
    storeId: Long,
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onMenuSelected: (String) -> Unit,
    onDeleteMenuClick: () -> Unit,
    onEditModClick: (Long, String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val storeState by viewModel.storeState.collectAsStateWithLifecycle()

    LaunchedEffect(storeId) {
        viewModel.fetchStoreDetail(storeId)
    }

    EditMenuScreen(
        storeDetailState = storeState.storeDetail,
        menuItems = storeState.menuItems,
        onMenuSelected = onMenuSelected,
        onDeleteMenuClick = onDeleteMenuClick,
        onEditModClick = onEditModClick,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun EditMenuScreen(
    storeDetailState: UiState<Any>,
    menuItems: List<MenuItem>,
    onMenuSelected: (String) -> Unit,
    onDeleteMenuClick: () -> Unit,
    onEditModClick: (Long, String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var selectedMenu by remember { mutableStateOf<MenuItem?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.9f)
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
                Text(
                    text = "어떤 메뉴를 편집할까요?",
                    style = HankkiTheme.typography.suitH2,
                    color = Gray900,
                    modifier = Modifier.padding(start = 22.dp)
                )

                when (storeDetailState) {
                    UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                        ) {
                            HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    is UiState.Success -> {
                        menuItems.forEach { menuItem ->
                            MenuItemComponent(
                                menuItem = menuItem,
                                selectedMenu = selectedMenu?.name,
                                onMenuSelected = {
                                    selectedMenu = menuItem
                                    onMenuSelected(menuItem.name)
                                }
                            )
                        }
                    }

                    UiState.Failure -> {}
                }
            }

            SegmentedButton(
                option1 = "삭제하기",
                option2 = "수정하기",
                onOptionSelected = { selectedOption ->
                    if (selectedOption == "삭제하기") {
                        onDeleteMenuClick()
                    } else if (selectedOption == "수정하기") {
                        selectedMenu?.let { menu ->
                            onEditModClick(menu.id, menu.name, menu.price.toString())
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.0675f)
            )

            Spacer(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .navigationBarsPadding()
            )
        }

//        if (selectedMenu == null) {
//            BottomBlurLayout(
//                imageBlur = com.hankki.core.designsystem.R.drawable.img_white_gradient_bottom_middle,
//                modifier = Modifier.align(Alignment.BottomCenter)
//            )
//        }

    }
}
