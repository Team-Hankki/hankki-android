package com.hankki.data.home.dto.response

import com.hankki.domain.home.entity.response.UniversityResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversityResponseDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double,
) {
    fun toEntity() = UniversityResponseEntity(
        id = id,
        name = name,
        longitude = longitude,
        latitude = latitude
    )
}
