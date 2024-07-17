package com.hankki.feature.home.model

import com.hankki.domain.home.entity.response.StorePinEntity

data class PinModel(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)

fun StorePinEntity.toModel() = PinModel(
    id = id,
    name = name,
    latitude = latitude,
    longitude = longitude
)
