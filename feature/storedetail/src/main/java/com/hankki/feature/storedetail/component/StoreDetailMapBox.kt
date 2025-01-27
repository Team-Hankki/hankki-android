package com.hankki.feature.storedetail.component

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.storedetail.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun StoreDetailMapBox(
    latitude: Double,
    longitude: Double,
) {
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(latitude, longitude), 17.0)
    }

    LaunchedEffect(latitude, longitude) {
        if (Geocoder.isPresent()) {
            val geocoder = Geocoder(context)
            try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    address = addresses[0].getAddressLine(0).replace("대한민국 ", "")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                address = "-"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        if (address == "-") {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_map_error),
                contentDescription = "지도 로드 실패",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .aspectRatio(1.5f),
                contentScale = ContentScale.Crop
            )
        } else {
            NaverMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .aspectRatio(1.5f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapType = MapType.Basic,
                    locationTrackingMode = LocationTrackingMode.None,
                    minZoom = 5.0,
                    maxZoom = 20.0,
                ),
                uiSettings = MapUiSettings(
                    isCompassEnabled = false,
                    isZoomControlEnabled = false,
                    isScaleBarEnabled = false,
                    isScrollGesturesEnabled = true,
                    isZoomGesturesEnabled = true,
                    isTiltGesturesEnabled = false,
                    isRotateGesturesEnabled = false,
                )
            ) {
                MapMarker(latitude = latitude, longitude = longitude)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .border(
                    1.dp,
                    Gray100,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "주소",
                    style = HankkiTheme.typography.caption4,
                    color = Gray400
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = address,
                    style = HankkiTheme.typography.caption4,
                    color = Gray700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(color = Gray100)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "복사",
                    style = HankkiTheme.typography.caption5,
                    color = if (address == "-") Gray300 else Gray600,
                    modifier = Modifier.noRippleClickable {
                        val clipboardManager =
                            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("address", address)
                        clipboardManager.setPrimaryClip(clip)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapMarker(latitude: Double, longitude: Double) {
    Marker(
        state = MarkerState(position = LatLng(latitude, longitude)),
        icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
    )
}
