package com.hankki.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.hankki.core.designsystem.theme.Typography
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

@OptIn(ExperimentalNaverMapApi::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeRoute(paddingValues: PaddingValues) {
    val context = LocalContext.current
    val sungsil = LatLng(37.494705526855, 126.95994559383)
    val DEFAULT_ZOOM = 16.0

    val focusLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            sungsil,
            DEFAULT_ZOOM
        )
    }


    HomeScreen(cameraPositionState, paddingValues) {
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

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(
    cameraPositionState: CameraPositionState,
    paddingValues: PaddingValues,
    reposition: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.BottomEnd
    ) {
        NaverMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            cameraPositionState = cameraPositionState,
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                mapType = MapType.Basic, // 지도 모양
                locationTrackingMode = LocationTrackingMode.NoFollow, // 위치 추적 모드
            ),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                isScaleBarEnabled = false
            )
        ) {
            // Markers
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = reposition
        ) {
            Text(
                text = "+",
                style = Typography.bodyLarge
            )
        }
    }
}
