package com.hankki.feature.home.model

import com.hankki.domain.home.entity.response.UniversityResponseEntity

data class UniversityModel(
    val id: Long = 0L,
    val name: String? = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
)

fun UniversityResponseEntity.toModel() = UniversityModel(
    id = id,
    name = name,
    longitude = longitude,
    latitude = latitude
)
