package com.hankki.feature.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.LocationServices
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.amplitude.PropertyKey
import com.hankki.core.common.extension.ignoreNextModifiers
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.EmptyImageWithText
import com.hankki.core.designsystem.component.layout.EmptyView
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiHeadTopBar
import com.hankki.core.designsystem.event.LocalButtonSnackBarTrigger
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.MapConstants.CAN_SEE_TITLE_ZOOM
import com.hankki.feature.home.MapConstants.DEFAULT_ZOOM
import com.hankki.feature.home.R.drawable.ic_marker
import com.hankki.feature.home.component.HankkiCategoryScrollableTabRow
import com.hankki.feature.home.component.HankkiFilterBottomSheet
import com.hankki.feature.home.component.HomeStoreItem
import com.hankki.feature.home.component.RepositionButton
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.HomeChips
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

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToUniversitySelection: () -> Unit,
    navigateStoreDetail: (Long) -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackBar = LocalSnackBarTrigger.current
    val buttonSnackBar = LocalButtonSnackBarTrigger.current

    val focusLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(state.myUniversityModel.latitude, state.myUniversityModel.longitude),
            DEFAULT_ZOOM
        )
    }

    var backPressedTime by remember {
        mutableLongStateOf(0L)
    }

    BackHandler(enabled = true) {
        if (System.currentTimeMillis() - backPressedTime <= 2000L) {
            (context as Activity).finish()
        } else {
            snackBar("한 번 더 누르시면 앱이 종료됩니다.")
        }
        backPressedTime = System.currentTimeMillis()
    }

    // 대학 선택 후 home 재진입시 변경된 대학 설정 반영을 위해 존재
    // 화면 전환시 매개변수 넘기는 방법으로 개선 가능
    LaunchedEffect(Unit) {
        viewModel.getUniversityInformation()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.ShowSnackBar -> buttonSnackBar(
                    sideEffect.message,
                    sideEffect.jogboId
                )

                is HomeSideEffect.MoveMap -> {
                    cameraPositionState.move(
                        CameraUpdate.scrollAndZoomTo(
                            LatLng(sideEffect.latitude, sideEffect.longitude),
                            DEFAULT_ZOOM
                        ).animate(CameraAnimation.Fly)
                    )
                }

                is HomeSideEffect.MoveMyLocation -> {
                    focusLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                        if (location == null) {
                            snackBar("위치 정보를 가져올 수 없습니다.")
                            return@addOnSuccessListener
                        }

                        viewModel.moveMap(location.latitude, location.longitude)
                    }.addOnFailureListener {
                        viewModel.moveMap(
                            latitude = state.myUniversityModel.latitude,
                            longitude = state.myUniversityModel.longitude
                        )
                    }
                }
            }
        }
    }

    HomeScreen(
        isOpenDialog = state.isOpenDialog,
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        universityName = state.myUniversityModel.name ?: "전체",
        selectedStoreItem = state.selectedStoreItem,
        storeItemState = state.storeItems,
        jogboItems = state.jogboItems,
        markerItems = state.markerItems,
        categoryChipItems = state.categoryChipItems,
        priceChipState = state.priceChipState,
        priceChipItems = state.priceChipItems,
        sortChipState = state.sortChipState,
        sortChipItems = state.sortChipItems,
        isMainBottomSheetOpen = state.isMainBottomSheetOpen,
        isMyJogboBottomSheetOpen = state.isMyJogboBottomSheetOpen,
        isFilterBottomSheetOpen = state.isFilterBottomSheetOpen,
        navigateStoreDetail = navigateStoreDetail,
        dialogNegativeClicked = { viewModel.setDialog(false) },
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
        clearChipFocus = viewModel::clearChipFocus,
        selectHomeChipItem = viewModel::selectHomeChipItem,
        setChipItems = viewModel::setChipItem,
        controlFilterBottomSheetState = viewModel::controlFilterBottomSheetState,
        addNewJogbo = navigateToAddNewJogbo,
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
            viewModel.moveMyLocation()
        }
    }
}

@OptIn(
    ExperimentalNaverMapApi::class,
    ExperimentalMaterialApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    isOpenDialog: Boolean,
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    universityName: String,
    selectedStoreItem: StoreItemModel?,
    storeItemState: EmptyUiState<PersistentList<StoreItemModel>>,
    jogboItems: PersistentList<JogboResponseModel>,
    markerItems: PersistentList<PinModel>,
    categoryChipItems: PersistentList<CategoryChipItem>,
    priceChipState: ChipState,
    priceChipItems: PersistentList<ChipItem>,
    sortChipState: ChipState,
    sortChipItems: PersistentList<ChipItem>,
    isMainBottomSheetOpen: Boolean,
    isMyJogboBottomSheetOpen: Boolean,
    isFilterBottomSheetOpen: Boolean,
    dialogNegativeClicked: () -> Unit = {},
    dialogPositiveClicked: () -> Unit = {},
    selectStoreItem: (StoreItemModel) -> Unit = {},
    navigateStoreDetail: (Long) -> Unit = {},
    navigateToUniversitySelection: () -> Unit = {},
    controlMyJogboBottomSheet: () -> Unit = {},
    controlFilterBottomSheetState: () -> Unit = {},
    clickMarkerItem: (Long) -> Unit = {},
    clickMap: () -> Unit = {},
    clearChipFocus: () -> Unit = {},
    selectHomeChipItem: (chip: HomeChips, name: String, tag: String) -> Unit = { _, _, _ -> },
    setChipItems: (priceItem: String, priceTag: String, sortItem: String, sortTag: String) -> Unit = { _, _, _, _ -> },
    getJogboItems: (Long) -> Unit = {},
    addNewJogbo: () -> Unit = {},
    addStoreAtJogbo: (Long, Long) -> Unit = { _, _ -> },
    reposition: () -> Unit = {},
) {
    val tracker = LocalTracker.current
    val bottomSheetState =
        rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val height by rememberSaveable {
        mutableFloatStateOf(configuration.screenHeightDp * BOTTOM_SHEET_HEIGHT_RATIO)
    }

    LaunchedEffect(
        key1 = bottomSheetState.currentValue,
        key2 = storeItemState,
        LocalLifecycleOwner.current
    ) {
        if (bottomSheetState.isCollapsed || storeItemState is EmptyUiState.Success) {
            listState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(key1 = bottomSheetState.progress) {
        if (!bottomSheetState.isExpanded) {
            clearChipFocus()
        }
    }

    if (isOpenDialog) {
        DoubleButtonDialog(
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
        LaunchedEffect(key1 = Unit) {
            if (selectedStoreItem != null) {
                getJogboItems(selectedStoreItem.id)
            }
        }

        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            onDismissRequest = controlMyJogboBottomSheet,
            addNewJogbo = addNewJogbo,
            onAddJogbo = { jogboId ->
                addStoreAtJogbo(jogboId, selectedStoreItem?.id ?: 0L)
                tracker.track(
                    type = EventType.ADD,
                    name = "Home_RestList_Jokbo"
                )
            }
        )
    }

    if (isFilterBottomSheetOpen) {
        HankkiFilterBottomSheet(
            priceFilter = priceChipItems,
            sortFilter = sortChipItems,
            selectedPriceItem = ChipItem(
                name = priceChipState.title,
                tag = priceChipState.tag
            ),
            selectedSortItem = ChipItem(
                name = sortChipState.title,
                tag = sortChipState.tag
            ),
            onDismissRequest = {
                controlFilterBottomSheetState()
            }
        ) { priceItem, sortItem ->
            setChipItems(
                priceItem.name,
                priceItem.tag,
                sortItem.name,
                sortItem.tag
            )

            controlFilterBottomSheetState()
        }
    }

    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        HankkiHeadTopBar(
            content = {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.noRippleClickable(navigateToUniversitySelection),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(18.dp))
                        Text(
                            text = universityName,
                            style = HankkiTheme.typography.body5,
                            color = Gray900
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(id = com.hankki.feature.home.R.drawable.ic_arrow_down_16_black),
                            contentDescription = "button",
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .size(16.dp),
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            },
            modifier = Modifier.background(White)
        )

        HankkiCategoryScrollableTabRow(
            categoryChipItems = categoryChipItems,
            onClickItem = selectHomeChipItem,
            isDefaultFilter = sortChipState.tag == defaultSortChipState.tag && priceChipState.tag == defaultPriceChipState.tag,
        ) {
            controlFilterBottomSheetState()
        }

        Box {
            NaverMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        animateFloatAsState(
                            targetValue = if (isMainBottomSheetOpen) 1f - BOTTOM_SHEET_HEIGHT_RATIO else 1f,
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
                    isCompassEnabled = false,
                    isZoomControlEnabled = false,
                    isScaleBarEnabled = false,
                    logoGravity = Gravity.TOP or Gravity.END
                ),
                onMapClick = { _, _ ->
                    clickMap()
                }
            ) {
                markerItems.forEach { marker ->
                    if (selectedStoreItem == null || marker.id == selectedStoreItem.id) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    marker.latitude,
                                    marker.longitude
                                )
                            ),
                            icon = OverlayImage.fromResource(ic_marker),
                            captionText = if (cameraPositionState.position.zoom > CAN_SEE_TITLE_ZOOM) marker.name else "",
                            onClick = {
                                clickMarkerItem(marker.id)
                                tracker.track(
                                    type = EventType.CLICK,
                                    name = "Home_Map_Pin",
                                    properties = mapOf(
                                        PropertyKey.STORE to marker.name
                                    )
                                )
                                true
                            }
                        )
                    }
                }
            }

            Column {
                Spacer(modifier = Modifier.height(12.dp))

                BottomSheetScaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .ignoreNextModifiers(),
                    sheetBackgroundColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    sheetShape = RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp
                    ),
                    sheetGesturesEnabled = true,
                    sheetContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(White)
                                .sizeIn(minHeight = height.dp),
                        ) {
                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_drag_handle),
                                contentDescription = "drag handle",
                                tint = Gray200,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            when (storeItemState) {
                                EmptyUiState.Empty -> {
                                    EmptyImageWithText(
                                        text = "조건에 맞는 식당이 없어요",
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)

                                    )
                                }

                                EmptyUiState.Failure -> {
                                    EmptyImageWithText(
                                        text = stringResource(id = R.string.error_text),
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)

                                    )
                                }

                                EmptyUiState.Loading -> {
                                    Spacer(modifier = Modifier.height((height / 3).dp))
                                    HankkiLoadingScreen(modifier = Modifier.align(Alignment.CenterHorizontally))
                                }

                                is EmptyUiState.Success -> {
                                    val storeItems by remember { mutableStateOf(storeItemState.data) }

                                    Row(
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ) {
                                        Text(
                                            text = "${storeItems.size}개의 족보",
                                            style = HankkiTheme.typography.body8,
                                            color = Gray800,
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(5.dp))

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(White),
                                        state = listState
                                    ) {
                                        items(
                                            items = storeItems
                                        ) { item ->
                                            HomeStoreItem(
                                                storeId = item.id,
                                                storeImageUrl = item.imageUrl,
                                                category = item.category,
                                                storeName = item.name,
                                                price = item.lowestPrice,
                                                heartCount = item.heartCount,
                                                onClickItem = { id ->
                                                    navigateStoreDetail(id)
                                                    tracker.track(
                                                        type = EventType.CLICK,
                                                        name = "Home_StoreCard",
                                                        properties = mapOf(
                                                            PropertyKey.STORE to item.name
                                                        )
                                                    )
                                                }
                                            ) {
                                                selectStoreItem(item)
                                                controlMyJogboBottomSheet()
                                            }

                                            if (item == storeItems.last()) {
                                                Spacer(modifier = Modifier.height(12.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    scaffoldState = bottomSheetScaffoldState,
                    sheetPeekHeight = animateDpAsState(
                        targetValue = if (isMainBottomSheetOpen) height.dp else 0.dp,
                        animationSpec = tween(durationMillis = 300),
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
                                    modifier = Modifier.padding(
                                        bottom = 12.dp,
                                        end = 22.dp
                                    ),
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
                                        modifier = Modifier.padding(end = 22.dp),
                                        onClick = reposition
                                    )
                                }

                                if (selectedStoreItem != null) {
                                    HomeStoreItem(
                                        storeId = selectedStoreItem.id,
                                        storeImageUrl = selectedStoreItem.imageUrl,
                                        category = selectedStoreItem.category,
                                        storeName = selectedStoreItem.name,
                                        price = selectedStoreItem.lowestPrice,
                                        heartCount = selectedStoreItem.heartCount,
                                        modifier = Modifier.padding(
                                            horizontal = 22.dp,
                                            vertical = 12.dp
                                        ),
                                        onClickItem = navigateStoreDetail
                                    ) {
                                        controlMyJogboBottomSheet()
                                        getJogboItems(selectedStoreItem.id)
                                    }
                                } else {
                                    EmptyView("이런! 오류가 발생했어요!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val BOTTOM_SHEET_HEIGHT_RATIO = 0.24f

private object MapConstants {
    const val DEFAULT_ZOOM = 14.0
    const val CAN_SEE_TITLE_ZOOM = 15.0
}
