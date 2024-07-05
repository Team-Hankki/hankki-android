package com.hankki.feature.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.google.android.gms.location.LocationServices
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.feature.home.MapConstants.DEFAULT_ZOOM
import com.hankki.feature.home.component.ChipState
import com.hankki.feature.home.component.DropdownFilterChip
import com.hankki.feature.home.component.HankkiTopBar
import com.hankki.feature.home.component.RepositionButton
import com.hankki.feature.home.component.RowFilterChip
import com.hankki.feature.home.component.StoreItem
import com.hankki.feature.home.component.StoreItemEntity
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
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    onShowSnackBar: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
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
        storeItems = state.storeItems,
        jogboItems = state.jogboItems,
        categoryChipState = state.categoryChipState,
        categoryChipItems = state.categoryChipItems,
        priceChipState = state.priceChipState,
        priceChipItems = state.priceChipItems,
        sortChipState = state.sortChipState,
        sortChipItems = state.sortChipItems,
        isMainBottomSheetOpen = state.isMainBottomSheetOpen,
        isMyJogboBottomSheetOpen = state.isMyJogboBottomSheetOpen,
        controlMyJogboBottomSheet = viewModel::controlMyJogboBottomSheet,
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
        // 이걸 sideEffect로 빼보자...?
        // 그럼 처음 서버로부터 받아오고도 이게 siceEffect인것 같은데???
        // 계속해서 사용자가 이동하는 값을 가지고있을 필요도 없고, 한번 이동시키면 끝이니까!!!
        focusLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            cameraPositionState.move(
                CameraUpdate.scrollAndZoomTo(
                    LatLng(location.latitude, location.longitude),
                    DEFAULT_ZOOM
                ).animate(CameraAnimation.Fly)
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    universityName: String,
    storeItems: List<StoreItemEntity>,
    jogboItems: List<JogboItemEntity>,
    categoryChipState: ChipState,
    categoryChipItems: List<String>,
    priceChipState: ChipState,
    priceChipItems: List<String>,
    sortChipState: ChipState,
    sortChipItems: List<String>,
    isMainBottomSheetOpen: Boolean,
    isMyJogboBottomSheetOpen: Boolean,
    controlMyJogboBottomSheet: () -> Unit = {},
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
        HankkiTopBar(universityName) {
            // TODO: 학교 선택 Screen 이동
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.74f)
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
                )
            ) {
                // Markers
            }

            Column {
                Row(
                    modifier = Modifier.padding(start = 22.dp, top = 12.dp),
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
                if (isMainBottomSheetOpen) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
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
                                ) {
                                    items(storeItems) {
                                        StoreItem(
                                            storeImageUrl = it.storeImageUrl,
                                            category = it.category,
                                            storeName = it.storeName,
                                            price = it.price,
                                            heartCount = it.heartCount
                                        ) {
                                            controlMyJogboBottomSheet()
                                            getJogboItems()
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            },
                            sheetDragHandle = { Spacer(modifier = Modifier.height(24.dp)) },
                            sheetContainerColor = Gray100,
                            sheetSwipeEnabled = false,
                            sheetPeekHeight = height
                        ) {}
                    }
                }
            }
        }

        AnimatedVisibility(visible = !isMainBottomSheetOpen) { // 애니메이션 뭐넣지... 하암...
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(120.dp)
                        .clip(
                            CircleShape
                        )
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("대충 가게 정보~", color = Color.Blue, fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.height(60.dp))
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
}
