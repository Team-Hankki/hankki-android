package com.hankki.feature.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.LocationServices
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
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
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.MarkerItem
import com.hankki.feature.home.model.StoreItemEntity
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
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    onShowSnackBar: (Int) -> Unit,
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
            state.latLng,
            DEFAULT_ZOOM
        )
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

    HomeScreen(
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState,
        universityName = state.universityName,
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
        controlMyJogboBottomSheet = viewModel::controlMyJogboBottomSheet,
        clickMarkerItem = { viewModel.clickMarkerItem(it) },
        clickMap = viewModel::clickMap,
        clickCategoryChip = viewModel::clickCategoryChip,
        selectCategoryChipItem = { viewModel.selectCategoryChipItem(it) },
        dismissCategoryChip = viewModel::dismissCategoryChip,
        clickPriceChip = viewModel::clickPriceChip,
        selectPriceChipItem = { viewModel.selectPriceChipItem(it) },
        dismissPriceChip = viewModel::dismissPriceChip,
        clickSortChip = viewModel::clickSortChip,
        selectSortChipItem = { viewModel.selectSortChipItem(it) },
        dismissSortChip = viewModel::dismissSortChip,
        getJogboItems = viewModel::getJogboItems,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: 추후 디자인 시스템 생성시 추가 예정
            // open Dialog
            // "설정하기" 누르면 아래처럼 이동할 예정
            Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", "com.hankki.hankkijogbo", null)
                context.startActivity(this)
            }
        } else {
            focusLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                viewModel.moveMap(location.latitude, location.longitude)
            }
        }
    }
}

@OptIn(
    ExperimentalNaverMapApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    universityName: String,
    selectedStoreItem: StoreItemEntity,
    storeItems: PersistentList<StoreItemEntity>,
    jogboItems: PersistentList<JogboItemEntity>,
    markerItems: PersistentList<MarkerItem>,
    categoryChipState: ChipState,
    categoryChipItems: PersistentList<CategoryChipItem>,
    priceChipState: ChipState,
    priceChipItems: PersistentList<String>,
    sortChipState: ChipState,
    sortChipItems: PersistentList<String>,
    isMainBottomSheetOpen: Boolean,
    isMyJogboBottomSheetOpen: Boolean,
    controlMyJogboBottomSheet: () -> Unit = {},
    clickMarkerItem: (Int) -> Unit = {},
    clickMap: () -> Unit = {},
    clickCategoryChip: () -> Unit = {},
    selectCategoryChipItem: (String) -> Unit = {},
    dismissCategoryChip: () -> Unit = {},
    clickPriceChip: () -> Unit = {},
    selectPriceChipItem: (String) -> Unit = {},
    dismissPriceChip: () -> Unit = {},
    clickSortChip: () -> Unit = {},
    selectSortChipItem: (String) -> Unit = {},
    dismissSortChip: () -> Unit = {},
    getJogboItems: () -> Unit = {},
    reposition: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = bottomSheetScaffoldState.bottomSheetState.currentValue) {
        if (bottomSheetScaffoldState.bottomSheetState.hasPartiallyExpandedState) {
            listState.animateScrollToItem(0)
        }
    }

    if (isMyJogboBottomSheetOpen) {
        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            onDismissRequest = controlMyJogboBottomSheet
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HankkiTopBar(
            content = {
                Row(
                    modifier = Modifier.noRippleClickable {
                        // TODO: 학교 선택 Screen 이동
                    }
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
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(
                        animateFloatAsState(
                            targetValue = if (isMainBottomSheetOpen) 0.74f else 1f, label = "Map"
                        ).value
                    )
                    .padding(),
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
                        state = MarkerState(position = LatLng(marker.x, marker.y)),
                        captionText = if (cameraPositionState.position.zoom > CAN_SEE_TITLE_ZOOM) "한끼네 한정식" else "",
                        onClick = {
                            clickMarkerItem(marker.id)
                            true
                        }
                    )
                }
            }

            Column {
                FlowRow(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RowFilterChip(
                        chipState = categoryChipState,
                        defaultTitle = "종류",
                        menus = categoryChipItems,
                        onDismissRequest = dismissCategoryChip,
                        onClickMenu = {
                            selectCategoryChipItem(it)
                        },
                        onClickChip = {
                            clickCategoryChip()
                            closeBottomSheet(coroutineScope, bottomSheetScaffoldState)
                        }
                    )

                    DropdownFilterChip(
                        chipState = priceChipState,
                        defaultTitle = "가격대",
                        menus = priceChipItems,
                        onDismissRequest = dismissPriceChip,
                        onClickMenu = {
                            selectPriceChipItem(it)
                        },
                        onClickChip = {
                            clickPriceChip()
                            closeBottomSheet(coroutineScope, bottomSheetScaffoldState)
                        }
                    )

                    DropdownFilterChip(
                        chipState = sortChipState,
                        defaultTitle = "정렬",
                        menus = sortChipItems,
                        onDismissRequest = dismissSortChip,
                        onClickMenu = {
                            selectSortChipItem(it)
                        },
                        onClickChip = {
                            clickSortChip()
                            closeBottomSheet(coroutineScope, bottomSheetScaffoldState)
                        }
                    )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (isMainBottomSheetOpen) {
                        val height = (LocalConfiguration.current.screenHeightDp * 0.3).dp
                        RepositionButton(
                            height = height,
                            onClick = reposition
                        )

                        BottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            sheetContent = {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    state = listState
                                ) {
                                    items(storeItems) { item ->
                                        StoreItem(
                                            storeImageUrl = item.storeImageUrl,
                                            category = item.category,
                                            storeName = item.storeName,
                                            price = item.price,
                                            heartCount = item.heartCount
                                        ) {
                                            controlMyJogboBottomSheet()
                                            getJogboItems()
                                        }

                                        if (item == storeItems.last()){
                                            Spacer(modifier = Modifier.height(12.dp))
                                        }
                                    }
                                }
                            },
                            sheetContainerColor = White,
                            sheetSwipeEnabled = true,
                            sheetPeekHeight = height
                        ) {}
                    } else {
                        Column {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.BottomEnd
                            ){
                                RepositionButton(
                                    height = 0.dp,
                                    onClick = reposition
                                )
                            }

                            StoreItem(
                                storeImageUrl = selectedStoreItem.storeImageUrl,
                                category = selectedStoreItem.category,
                                storeName = selectedStoreItem.storeName,
                                price = selectedStoreItem.price,
                                heartCount = selectedStoreItem.heartCount,
                                modifier = Modifier.padding(22.dp)
                            ) {
                                controlMyJogboBottomSheet()
                                getJogboItems()
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun closeBottomSheet(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
) {
    coroutineScope.launch {
        bottomSheetScaffoldState.bottomSheetState.partialExpand()
    }
}

private object MapConstants {
    const val DEFAULT_ZOOM = 16.0
    const val CAN_SEE_TITLE_ZOOM = 18.0
}
