package com.hankki.feature.report.model

data class LocationModel(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val location: String = "",
    val address: String = "",
)
