package com.hankki.feature.report.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.animateScrollAroundItem
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.AddPhotoButton
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreNameSearchButton
import com.hankki.core.designsystem.component.button.StoreNameSelectedButton
import com.hankki.core.designsystem.component.chip.HankkiChipWithIcon
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.textfield.HankkiMenuTextField
import com.hankki.core.designsystem.component.textfield.HankkiPriceTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.feature.report.main.component.SelectedImageController
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.MenuModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReportRoute(
    location: LocationModel,
    navigateUp: () -> Unit,
    navigateSearchStore: () -> Unit,
    navigateToReportFinish: (
        count: Long,
        storeName: String,
        storeId: Long,
    ) -> Unit,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.selectImageUri(uri)
            }
        }

    LaunchedEffect(key1 = lifecycleOwner) {
        if (location.location.isNotEmpty()) {
            viewModel.setLocation(location)
        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is ReportSideEffect.NavigateReportFinish -> navigateToReportFinish(
                    sideEffect.count,
                    sideEffect.storeName,
                    sideEffect.storeId
                )

                ReportSideEffect.UniversityError -> navigateUp()
            }
        }
    }

    ReportScreen(
        count = state.count,
        location = location,
        buttonEnabled = state.buttonEnabled,
        navigateUp = navigateUp,
        categoryList = state.categoryList,
        selectedImageUri = state.selectedImageUri,
        isUniversityError = state.isUniversityError,
        onDismissDialog = { viewModel.universityErrorSideEffect() },
        selectImageUri = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        clearSelectedImageUri = { viewModel.selectImageUri(null) },
        selectedCategory = state.selectedCategory,
        selectCategory = viewModel::selectCategory,
        menuList = state.menuList,
        changeMenuName = viewModel::changeMenuName,
        changePrice = viewModel::changePrice,
        addMenu = viewModel::addMenu,
        deleteMenu = viewModel::deleteMenu,
        navigateSearchStore = navigateSearchStore,
        submitReport = viewModel::submitReport
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReportScreen(
    count: Long,
    location: LocationModel,
    buttonEnabled: Boolean,
    navigateUp: () -> Unit,
    categoryList: PersistentList<CategoryEntity>,
    selectedImageUri: Uri?,
    isUniversityError: Boolean,
    onDismissDialog: () -> Unit = {},
    selectImageUri: () -> Unit,
    clearSelectedImageUri: () -> Unit = {},
    selectedCategory: String?,
    selectCategory: (String) -> Unit,
    menuList: PersistentList<MenuModel>,
    changeMenuName: (Int, String) -> Unit,
    changePrice: (Int, String) -> Unit,
    addMenu: () -> Unit,
    deleteMenu: (Int) -> Unit,
    navigateSearchStore: () -> Unit = {},
    submitReport: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val isVisibleIme = WindowInsets.isImeVisible

    if (isUniversityError) {
        SingleButtonDialog(
            title = "대학교를 먼저 선택해주세요",
            buttonTitle = "확인",
            onConfirmation = onDismissDialog
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .addFocusCleaner(focusManager)
    ) {
        HankkiTopBar(
            modifier = Modifier.background(White),
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
                .background(White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {
                Spacer(modifier = Modifier.height(18.dp))

                ReportTopContent(
                    count = count,
                    location = location.location,
                    onClick = navigateSearchStore
                )

                Spacer(modifier = Modifier.height(26.dp))
                HorizontalDivider(
                    thickness = 12.dp,
                    color = Gray100
                )
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                        .imePadding()
                ) {
                    StoreCategoryChips(
                        title = stringResource(id = com.hankki.feature.report.R.string.show_store_categories),
                        selectedItem = selectedCategory,
                        categories = categoryList
                    ) { item ->
                        selectCategory(item)
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = com.hankki.feature.report.R.string.add_menu_title),
                            style = HankkiTheme.typography.sub2,
                            color = Gray900
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = stringResource(id = com.hankki.feature.report.R.string.add_menu_sub_title),
                            style = HankkiTheme.typography.body4,
                            color = Gray400
                        )


                        if (selectedImageUri == null) {
                            Spacer(modifier = Modifier.height(24.dp))
                            AddPhotoButton(
                                onClick = selectImageUri,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                        } else {
                            Spacer(modifier = Modifier.height(20.dp))
                            SelectedImageController(
                                imageUri = selectedImageUri,
                                deleteImage = clearSelectedImageUri,
                                changeImage = selectImageUri
                            )
                            Spacer(modifier = Modifier.height(42.dp))
                        }


                        menuList.forEachIndexed { index, menu ->
                            MenuWithPriceInputComponent(
                                name = menu.name,
                                price = menu.price,
                                isPriceError = menu.isPriceError,
                                onMenuChange = { menuName ->
                                    changeMenuName(index, menuName)
                                },
                                onPriceChange = { price ->
                                    changePrice(index, price)
                                },
                                deleteMenu = { deleteMenu(index) },
                                modifier = Modifier.animateScrollAroundItem(scrollState)
                            )
                            if (index != menuList.lastIndex) {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        AddMenuButton(onClick = {
                            addMenu()
                            coroutineScope.launch {
                                delay(100)

                                if (isVisibleIme) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                } else {
                                    scrollState.animateScrollTo(scrollState.maxValue)
                                }
                            }
                        })

                        Spacer(modifier = Modifier.height(35.dp))
                        BottomBlurLayout()
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .noRippleClickable(),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomBlurLayout()
                Column {
                    HankkiButton(
                        text = stringResource(id = com.hankki.feature.report.R.string.do_report),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp),
                        onClick = submitReport,
                        enabled = buttonEnabled
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun ReportTopContent(
    count: Long,
    location: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = count.toString() + stringResource(id = com.hankki.feature.report.R.string.counted_report),
            style = HankkiTheme.typography.body4,
            color = if (location.isEmpty()) Red500 else Gray600,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f, fill = false)) {
                if (location.isEmpty()) {
                    StoreNameSearchButton(onClick = onClick)
                } else {
                    StoreNameSelectedButton(text = location, onClick = onClick)
                }
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(id = com.hankki.feature.report.R.string.will_report),
                style = HankkiTheme.typography.suitH3,
                color = Gray900,
                maxLines = 1
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
            style = HankkiTheme.typography.sub2,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                style = HankkiTheme.typography.body5,
                color = Color.Transparent
            )

            Spacer(modifier = Modifier.height(11.dp))

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
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = com.hankki.feature.report.R.string.add_menu),
            style = HankkiTheme.typography.body6,
            color = Gray400
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    HankkijogboTheme {
        ReportScreen(
            count = 1,
            buttonEnabled = false,
            location = LocationModel(),
            navigateUp = {},
            categoryList = persistentListOf(),
            selectedImageUri = null,
            selectImageUri = {},
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
            deleteMenu = {},
            navigateSearchStore = {},
            submitReport = {},
            isUniversityError = false,
            onDismissDialog = {}
        )
    }
}
