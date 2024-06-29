package com.hankki.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@Composable
fun HomeRoute(paddingValues: PaddingValues) {
    HomeScreen(paddingValues)
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    NaverMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        uiSettings = MapUiSettings(
            isZoomControlEnabled = false,
            isScaleBarEnabled = false
        )
    )
}
