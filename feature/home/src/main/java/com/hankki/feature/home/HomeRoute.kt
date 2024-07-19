package com.hankki.feature.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.LocationServices
import com.hankki.core.common.extension.ignoreNextModifiers
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.DoubleCenterButtonDialog
import com.hankki.core.designsystem.component.dialog.ImageDoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.MapConstants.CAN_SEE_TITLE_ZOOM
import com.hankki.feature.home.MapConstants.DEFAULT_ZOOM
import com.hankki.feature.home.component.DropdownFilterChip
import com.hankki.feature.home.component.RepositionButton
import com.hankki.feature.home.component.RowFilterChip
import com.hankki.feature.home.component.StoreItem
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.PinModel
import com.hankki.feature.home.model.StoreItemModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    onShowSnackBar: (Int) -> Unit,
    navigateToUniversitySelection: () -> Unit,
    navigateStoreDetail: (Long) -> Unit,
    isNewUniversity: Boolean,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(state.myUniversityModel.latitude, state.myUniversityModel.longitude),
            DEFAULT_ZOOM
        )
    }

    LaunchedEffect(key1 = true) {
        // if (isNewUniversity) {
            viewModel.getUniversityInformation()
//        }else {
//            viewModel.fetchData()
//        }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.SnackBar -> onShowSnackBar(sideEffect.message)
                is HomeSideEffect.MoveMap -> {
                    cameraPositionState.move(
                        CameraUpdate.scrollAndZoomTo(
                            LatLng(sideEffect.latitude, sideEffect.longitude),
                            DEFAULT_ZOOM
                        ).animate(CameraAnimation.Fly)
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = state.categoryChipState,
        key2 = state.priceChipState,
        key3 = state.sortChipState
    ) {
        if (state.categoryChipState !is ChipState.Selected && state.priceChipState !is ChipState.Selected && state.sortChipState !is ChipState.Selected) {
            with(viewModel) {
                getStoreItems()
                getMarkerItems()
            }
        }
    }


    HomeScreen(
        isOpenDialog = state.isOpenDialog,
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        universityName = state.myUniversityModel.name ?: "전체",
        selectedStoreItem = state.selectedStoreItem,
        storeItems = state.storeItems,
        jogboItems = state.jogboItems,
        markerItems = state.markerItems,
        categoryChipState = state.categoryChipState,
        categoryChipItems = state.categoryChipItems,
        priceChipState = state.priceChipState,
        priceChipItems = state.priceChipItems,
        sortChipState = state.sortChipState,
        sortChipItems = state.sortChipItems,
        isMainBottomSheetOpen = state.isMainBottomSheetOpen,
        isMyJogboBottomSheetOpen = state.isMyJogboBottomSheetOpen,
        navigateStoreDetail = navigateStoreDetail,
        dialogNegativeClicked = {viewModel.setDialog(false)},
        dialogPositiveClicked = {
            viewModel.setDialog(false)
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", "com.hankki.hankkijogbo", null)
                context.startActivity(this)
            }
        },
        selectStoreItem = viewModel::selectStoreItem,
        navigateToUniversitySelection = navigateToUniversitySelection,
        controlMyJogboBottomSheet = viewModel::controlMyJogboBottomSheet,
        clickMarkerItem = viewModel::clickMarkerItem,
        clickMap = viewModel::clickMap,
        clickCategoryChip = viewModel::clickCategoryChip,
        selectCategoryChipItem = viewModel::selectCategoryChipItem,
        dismissCategoryChip = viewModel::dismissCategoryChip,
        clickPriceChip = viewModel::clickPriceChip,
        selectPriceChipItem = viewModel::selectPriceChipItem,
        dismissPriceChip = viewModel::dismissPriceChip,
        clickSortChip = viewModel::clickSortChip,
        selectSortChipItem = viewModel::selectSortChipItem,
        dismissSortChip = viewModel::dismissSortChip,
        getJogboItems = viewModel::getJogboItems,
        addStoreAtJogbo = viewModel::addStoreAtJogbo,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.setDialog(true)
        } else {
            focusLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                viewModel.moveMap(location.latitude, location.longitude)
            }
        }
    }
}

@OptIn(
    ExperimentalNaverMapApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    isOpenDialog: Boolean,
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    universityName: String,
    selectedStoreItem: StoreItemModel,
    storeItems: PersistentList<StoreItemModel>,
    jogboItems: PersistentList<JogboResponseModel>,
    markerItems: PersistentList<PinModel>,
    categoryChipState: ChipState,
    categoryChipItems: PersistentList<CategoryChipItem>,
    priceChipState: ChipState,
    priceChipItems: PersistentList<ChipItem>,
    sortChipState: ChipState,
    sortChipItems: PersistentList<ChipItem>,
    isMainBottomSheetOpen: Boolean,
    isMyJogboBottomSheetOpen: Boolean,
    dialogNegativeClicked: () -> Unit = {},
    dialogPositiveClicked: () -> Unit = {},
    selectStoreItem: (StoreItemModel) -> Unit = {},
    navigateStoreDetail: (Long) -> Unit = {},
    navigateToUniversitySelection: () -> Unit = {},
    controlMyJogboBottomSheet: () -> Unit = {},
    clickMarkerItem: (Long) -> Unit = {},
    clickMap: () -> Unit = {},
    clickCategoryChip: () -> Unit = {},
    selectCategoryChipItem: (String, String) -> Unit = { _, _ -> },
    dismissCategoryChip: () -> Unit = {},
    clickPriceChip: () -> Unit = {},
    selectPriceChipItem: (String, String) -> Unit = { _, _ -> },
    dismissPriceChip: () -> Unit = {},
    clickSortChip: () -> Unit = {},
    selectSortChipItem: (String, String) -> Unit = { _, _ -> },
    dismissSortChip: () -> Unit = {},
    getJogboItems: (Long) -> Unit = {},
    addStoreAtJogbo: (Long, Long) -> Unit = { _, _ -> },
    reposition: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val height by rememberSaveable {
        mutableDoubleStateOf(configuration.screenHeightDp * 0.3)
    }

    LaunchedEffect(
        key1 = bottomSheetState.currentValue,
        key2 = storeItems,
        LocalLifecycleOwner.current
    ) {
        if (bottomSheetState.isCollapsed) {
            listState.animateScrollToItem(0)
        }
    }
    
    if (isOpenDialog) {
        DoubleCenterButtonDialog(
            title = "설정 > 개인정보보호 >\n" +
                    "위치서비스와 설정 > 한끼족보에서\n" +
                    "위치 정보 접근을 모두 허용해 주세요.",
            negativeButtonTitle = "닫기",
            positiveButtonTitle = "설정하기",
            onNegativeButtonClicked = dialogNegativeClicked,
            onPositiveButtonClicked = dialogPositiveClicked,
        )
    }

    if (isMyJogboBottomSheetOpen) {
        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            onDismissRequest = controlMyJogboBottomSheet,
            onAddJogbo = { jogboId ->
                addStoreAtJogbo(jogboId, selectedStoreItem.id)
            }
        )
    }

    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        HankkiTopBar(
            content = {
                Row(
                    modifier = Modifier.noRippleClickable(navigateToUniversitySelection)
                ) {
                    Text(
                        text = universityName,
                        style = HankkiTheme.typography.suitH2,
                        color = Gray900
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown_btn),
                        contentDescription = "button",
                        tint = Gray300
                    )
                }
            },
            modifier = Modifier.background(White)
        )

        Box {
            NaverMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        animateFloatAsState(
                            targetValue = if (isMainBottomSheetOpen) 0.7f else 1f,
                            label = "Map"
                        ).value
                    ),
                cameraPositionState = cameraPositionState,
                locationSource = rememberFusedLocationSource(),
                properties = MapProperties(
                    mapType = MapType.Basic,
                    locationTrackingMode = LocationTrackingMode.NoFollow
                ),
                uiSettings = MapUiSettings(
                    isZoomControlEnabled = false,
                    isScaleBarEnabled = false
                ),
                onMapClick = { _, _ ->
                    clickMap()
                }
            ) {
                markerItems.forEach { marker ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                marker.latitude,
                                marker.longitude
                            )
                        ),
                        icon = OverlayImage.fromResource(R.drawable.ic_marker),
                        captionText = if (cameraPositionState.position.zoom > CAN_SEE_TITLE_ZOOM) marker.name else "",
                        onClick = {
                            clickMarkerItem(marker.id)
                            true
                        }
                    )
                }
            }

            Column {
                FlowRow(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RowFilterChip(
                        chipState = categoryChipState,
                        defaultTitle = "종류",
                        menus = categoryChipItems,
                        onDismissRequest = dismissCategoryChip,
                        onClickMenu = selectCategoryChipItem,
                        onClickChip = {
                            clickCategoryChip()
                            closeBottomSheet(
                                coroutineScope,
                                bottomSheetScaffoldState
                            )
                        }
                    )

                    DropdownFilterChip(
                        chipState = priceChipState,
                        defaultTitle = "가격대",
                        menus = priceChipItems,
                        onDismissRequest = dismissPriceChip,
                        onClickMenu = selectPriceChipItem,
                        onClickChip = {
                            clickPriceChip()
                            closeBottomSheet(
                                coroutineScope,
                                bottomSheetScaffoldState
                            )
                        }
                    )

                    DropdownFilterChip(
                        chipState = sortChipState,
                        defaultTitle = "정렬",
                        menus = sortChipItems,
                        onDismissRequest = dismissSortChip,
                        onClickMenu = selectSortChipItem,
                        onClickChip = {
                            clickSortChip()
                            closeBottomSheet(
                                coroutineScope,
                                bottomSheetScaffoldState
                            )
                        }
                    )
                }

                BottomSheetScaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 30.dp,
                                topEnd = 30.dp
                            )
                        )
                        .ignoreNextModifiers(),
                    sheetBackgroundColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    sheetGesturesEnabled = true,
                    sheetContent = {
                        AnimatedVisibility(
                            visible = isMainBottomSheetOpen,
                            enter = EnterTransition.None,
                            exit = fadeOut() + slideOut { IntOffset(0, it.height) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 30.dp,
                                            topEnd = 30.dp
                                        )
                                    )
                                    .fillMaxSize()
                                    .background(White)
                            ) {
                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_drag_handle),
                                    contentDescription = "drag handle",
                                    tint = Gray200,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(White),
                                    state = listState
                                ) {
                                    items(
                                        items = storeItems,
                                        key = { item -> item.id }
                                    ) { item ->
                                        StoreItem(
                                            storeId = item.id,
                                            storeImageUrl = item.imageUrl,
                                            category = item.category,
                                            storeName = item.name,
                                            price = item.lowestPrice,
                                            heartCount = item.heartCount,
                                            onClickItem = navigateStoreDetail
                                        ) {
                                            controlMyJogboBottomSheet()
                                            getJogboItems(item.id)
                                            selectStoreItem(item)
                                        }

                                        if (item == storeItems.last()) {
                                            Spacer(modifier = Modifier.height(12.dp))
                                        }
                                    }
                                }
                            }
                        }
                    },
                    scaffoldState = bottomSheetScaffoldState,
                    sheetPeekHeight = animateDpAsState(
                        targetValue = if (isMainBottomSheetOpen) height.dp else 0.dp,
                        label = ""
                    ).value,
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        if (isMainBottomSheetOpen) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                RepositionButton(
                                    height = height.dp,
                                    modifier = Modifier.padding(bottom = 19.dp, end = 19.dp),
                                    onClick = reposition
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.BottomEnd
                                ) {
                                    RepositionButton(
                                        height = 0.dp,
                                        modifier = Modifier.padding(end = 19.dp),
                                        onClick = reposition
                                    )
                                }

                                StoreItem(
                                    storeId = selectedStoreItem.id,
                                    storeImageUrl = selectedStoreItem.imageUrl,
                                    category = selectedStoreItem.category,
                                    storeName = selectedStoreItem.name,
                                    price = selectedStoreItem.lowestPrice,
                                    heartCount = selectedStoreItem.heartCount,
                                    modifier = Modifier.padding(22.dp),
                                    onClickItem = navigateStoreDetail
                                ) {
                                    controlMyJogboBottomSheet()
                                    getJogboItems(selectedStoreItem.id)
                                }
                                Spacer(modifier = Modifier.height(22.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun closeBottomSheet(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    coroutineScope.launch {
        bottomSheetScaffoldState.bottomSheetState.collapse()
    }
}

private object MapConstants {
    const val DEFAULT_ZOOM = 16.0
    const val CAN_SEE_TITLE_ZOOM = 16.0
}
