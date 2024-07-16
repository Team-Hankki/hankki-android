package com.hankki.feature.report.model

import com.hankki.domain.report.entity.LocationEntity

data class LocationModel(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val location: String = "",
    val address: String = "",
)

fun LocationEntity.toModel() = LocationModel(
    latitude = latitude.toFloat(),
    longitude = longitude.toFloat(),
    location = name,
    address = address
)
