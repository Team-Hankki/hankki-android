package com.hankki.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.home.MapConstants.DEFAULT_ZOOM
import com.hankki.feature.home.designsystem.ChipState
import com.hankki.feature.home.designsystem.HankkiFilterChip
import com.hankki.feature.home.designsystem.StoreItem
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeRoute(paddingValues: PaddingValues) {
    val context = LocalContext.current

    val focusLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            // 추후 대학의 위도, 경도를 서버에서 받아 넣을 예정이라 하드코딩 해놨습니다.
            LatLng(37.3009489417651, 127.03549529577874),
            DEFAULT_ZOOM
        )
    }

    HomeScreen(
        paddingValues = paddingValues,
        cameraPositionState = cameraPositionState
    ) {
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

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
    reposition: () -> Unit = {},
) {
    var coroutineScope = rememberCoroutineScope()
    var isOpenBottomSheet by remember { mutableStateOf(false) }
    var isOpenRealBottomSheet by remember { mutableStateOf(false) }

    var bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    if (isOpenRealBottomSheet) {
        ModalBottomSheet(onDismissRequest = { isOpenRealBottomSheet = false }) {
            Text(
                modifier = Modifier.padding(vertical = 80.dp),
                text = "오 진짜 나오네"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "건국대학교",
                style = HankkiTheme.typography.suitH1,
                color = Gray900
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown_btn),
                contentDescription = "button",
                tint = Gray300
            )
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
                var open by remember {
                    mutableStateOf(false)
                }
                Row(
                    modifier = Modifier.padding(start = 22.dp, top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HankkiFilterChip(
                        chipState = ChipState.UNSELECTED,
                        title = "종류",
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.partialExpand()
                            }
                            open = !open
                        }
                    )
                    DropdownFilterChip(
                        chipState = ChipState.SELECTED,
                        title = "가격대",
                        menus = listOf(
                            "6000원 이하",
                            "6000 ~ 8000원"
                        ),
                    ) {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.partialExpand()
                        }
                    }

                    DropdownFilterChip(
                        chipState = ChipState.FIXED,
                        title = "정렬",
                        menus = listOf(
                            "최신순",
                            "가격 낮은순",
                            "추천순"
                        )
                    ) {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.partialExpand()
                        }
                    }
                }
                if (!isOpenBottomSheet) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            val menus =
                                listOf("한식", "중식", "일식", "양식", "분식", "패스트푸드", "디저트", "카페", "기타")
                            if (open) {
                                Popup(
                                    onDismissRequest = { open = false },
                                ) {
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        item {
                                            Spacer(modifier = Modifier.width(22.dp))
                                        }
                                        items(menus) { menu ->
                                            Box(
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(White),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = menu,
                                                    style = HankkiTheme.typography.caption1,
                                                    color = Gray400
                                                )
                                            }
                                        }
                                        item {
                                            Spacer(modifier = Modifier.width(22.dp))
                                        }
                                    }
                                }

                            }
                        }
                        Column {
                            Box(
                                modifier = Modifier
                                    .padding(end = 21.dp, bottom = 14.dp)
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Gray300, CircleShape)
                                    .background(White)
                                    .padding(9.dp)
                                    .noRippleClickable(onClick = reposition)
                            ) {
                                AsyncImage(
                                    model = R.drawable.ic_map_here,
                                    contentDescription = "here"
                                )
                            }
                            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.3).dp))
                        }

                        BottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            sheetContent = {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    items(count = 100) { // TODO: 추후 data class로 분리시 key 추가 예정
                                        StoreItem(
                                            storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                                            category = "한식",
                                            storeName = "한끼네 한정식",
                                            price = 7900,
                                            heartCount = 300
                                        ) {
                                            isOpenRealBottomSheet = true
                                        }
                                    }
                                }
                            },
                            sheetDragHandle = { Spacer(modifier = Modifier.height(24.dp)) },
                            sheetContainerColor = Gray100,
                            sheetSwipeEnabled = false,
                            sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.3).dp
                        ) {}
                    }
                }
            }
        }


        if (isOpenBottomSheet) {
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

object MapConstants {
    const val DEFAULT_ZOOM = 16.0
}

@Composable
fun DropdownFilterChip(
    chipState: ChipState,
    title: String,
    menus: List<String> = emptyList(),
    onClick: () -> Unit = {},
) {
    var isOpenDropDownMenu by remember { mutableStateOf(false) }
    HankkiFilterChip(
        chipState = chipState,
        title = title,
        onClick = {
            isOpenDropDownMenu = true
        }
    ) {
        DropdownMenu(
            expanded = isOpenDropDownMenu, // chipState관련으로 수정해야함.
            onDismissRequest = { isOpenDropDownMenu = false },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Gray200, RoundedCornerShape(10.dp))
                .background(White),
            offset = DpOffset((-20).dp, 0.dp)
        ) {
            menus.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            style = HankkiTheme.typography.caption1,
                            color = Gray600
                        )
                    }, onClick = {
                        isOpenDropDownMenu = false
                        onClick()
                    }
                )
            }
        }
    }
}
