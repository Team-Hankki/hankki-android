package com.hankki.data.report.dto

import com.hankki.domain.report.entity.LocationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationsResponseDto(
    @SerialName("locations")
    val locations: List<Location>,
) {
    @Serializable
    data class Location(
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("name")
        val name: String,
        @SerialName("address")
        val address: String,
    )
}

fun LocationsResponseDto.toEntity() = locations.map {
    LocationEntity(
        latitude = it.latitude,
        longitude = it.longitude,
        name = it.name,
        address = it.address
    )
}
