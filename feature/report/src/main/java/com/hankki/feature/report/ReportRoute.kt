package com.hankki.feature.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.AddPhotoButton
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreNameSearchButton
import com.hankki.core.designsystem.component.chip.HankkiChipWithIcon
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.textfield.HankkiMenuTextField
import com.hankki.core.designsystem.component.textfield.HankkiPriceTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.feature.report.model.MenuModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReportRoute(
    navigateUp: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ReportScreen(
        navigateUp = navigateUp,
        categoryList = state.categoryList,
        selectedCategory = state.selectedCategory,
        selectCategory = { category -> viewModel.selectCategory(category) },
        menuList = state.menuList,
        changeMenuName = { index, menuName -> viewModel.changeMenuName(index, menuName) },
        changePrice = { index, price -> viewModel.changePrice(index, price) },
        addMenu = viewModel::addMenu,
        deleteMenu = { viewModel.deleteMenu(it) },
    )
}

@Composable
fun ReportScreen(
    navigateUp: () -> Unit,
    categoryList: PersistentList<CategoryEntity>,
    selectedCategory: String?,
    selectCategory: (String) -> Unit,
    menuList: PersistentList<MenuModel>,
    changeMenuName: (Int, String) -> Unit,
    changePrice: (Int, String) -> Unit,
    addMenu: () -> Unit,
    deleteMenu: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .addFocusCleaner(focusManager)
    ) {
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(navigateUp)
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {
                Spacer(modifier = Modifier.height(18.dp))

                ReportTopContent {
                    // TODO: Store 검색 화면 이동
                }

                Spacer(modifier = Modifier.height(26.dp))
                HorizontalDivider(
                    thickness = 12.dp,
                    color = Gray100
                )
                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                ) {
                    StoreCategoryChips(
                        title = stringResource(id = com.hankki.feature.report.R.string.show_store_categories),
                        selectedItem = selectedCategory,
                        categories = categoryList
                    ) { item ->
                        selectCategory(item)
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = com.hankki.feature.report.R.string.add_menu_title),
                            style = HankkiTheme.typography.sub1,
                            color = Gray900
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = stringResource(id = com.hankki.feature.report.R.string.add_menu_sub_title),
                            style = HankkiTheme.typography.body4,
                            color = Gray400
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                        AddPhotoButton(modifier = Modifier.fillMaxWidth()) {
                            // TODO: 사진 업로드
                        }
                        Spacer(modifier = Modifier.height(32.dp))

                        menuList.forEachIndexed() { index, menu ->
                            MenuWithPriceInputComponent(
                                name = menu.name,
                                price = menu.price,
                                onMenuChange = { menuName ->
                                    changeMenuName(index, menuName)
                                },
                                onPriceChange = { price ->
                                    changePrice(index, price)
                                },
                                deleteMenu = { deleteMenu(index) }
                            )
                            if (index != menuList.lastIndex) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        AddMenuButton(onClick = {
                            addMenu()
                            coroutineScope.launch {
                                delay(100)
                                scrollState.animateScrollTo(scrollState.maxValue)
                            }
                        })

                        Spacer(modifier = Modifier.height(35.dp))
                        BottomBlurLayout()
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                BottomBlurLayout()
                Column {
                    HankkiButton(
                        text = "제보하기",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        onClick = {
                            // TODO: 제보하기 api 연결
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun ReportTopContent(
    count: Int = 52,
    onClick: () -> Unit = {},
) {
    // TODO: 라이팅 변경 예정이라 하드코딩 해둠. 변경시 res로 추출 예정
    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "한끼족보의 ${count}번째",
            style = HankkiTheme.typography.body4,
            color = Red,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            StoreNameSearchButton(onClick = onClick)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "을 제보할래요",
                style = HankkiTheme.typography.body6,
                color = Gray900
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StoreCategoryChips(
    title: String,
    categories: PersistentList<CategoryEntity>,
    selectedItem: String?,
    onClick: (String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = HankkiTheme.typography.sub1,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(14.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { item ->
                HankkiChipWithIcon(
                    iconUrl = item.imageUrl,
                    title = item.name,
                    isSelected = item.tag == selectedItem,
                    onClick = { onClick(item.tag) }
                )
            }
        }
    }
}

@Composable
fun MenuWithPriceInputComponent(
    name: String,
    price: String,
    onMenuChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    deleteMenu: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // +버튼 정렬 하기
        Row(modifier = Modifier.weight(1f)) {
            HankkiMenuTextField(
                value = name,
                onTextChanged = onMenuChange,
                isFocused = false,
                modifier = Modifier.fillMaxWidth(0.55f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            HankkiPriceTextField(
                value = price,
                onTextChanged = onPriceChange,
                isFocused = false,
                isError = (((price.takeIf { it.isNotBlank() }?.toLong() ?: 0) >= 8000)),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.width(3.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_circle_x),
            contentDescription = "delete",
            tint = Gray300,
            modifier = Modifier
                .size(32.dp)
                .noRippleClickable(onClick = deleteMenu)
        )
    }
}

@Composable
fun AddMenuButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier.noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_circle_dark_plus),
            contentDescription = "plus",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "메뉴 추가하기",
            style = HankkiTheme.typography.sub3,
            color = Gray400
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    HankkijogboTheme {
        ReportScreen(
            navigateUp = {},
            categoryList = persistentListOf(),
            selectCategory = {},
            selectedCategory = null,
            menuList = persistentListOf(
                MenuModel(
                    name = "김치찌개",
                    price = "8000"
                )
            ),
            changeMenuName = { _, _ -> },
            changePrice = { _, _ -> },
            addMenu = {},
            deleteMenu = {}
        )
    }
}
