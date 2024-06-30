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
import com.hankki.feature.home.MapConstants.DEFAULT_ZOOM
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

    val focusLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            // 추후 대학의 위도, 경도를 서버에서 받아 넣을 예정이라 하드코딩 해놨습니다.
            LatLng(37.494705526855, 126.95994559383),
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

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    cameraPositionState: CameraPositionState,
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

object MapConstants {
    const val DEFAULT_ZOOM = 16.0
}
