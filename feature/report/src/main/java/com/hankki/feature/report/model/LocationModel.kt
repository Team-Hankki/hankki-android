package com.hankki.feature.report.model

import com.hankki.domain.report.entity.LocationEntity

data class LocationModel(
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val location: String = "",
    val address: String = "",
)

fun LocationEntity.toModel() = LocationModel(
    latitude = latitude.toString(),
    longitude = longitude.toString(),
    location = name,
    address = address
)
