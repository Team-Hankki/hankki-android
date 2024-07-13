package com.hankki.feature.report.model

data class LocationModel(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val location: String = "",
    val address: String = ""
)
