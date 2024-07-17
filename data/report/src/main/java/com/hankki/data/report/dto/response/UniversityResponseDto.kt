package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.response.UniversityResponseEntity
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
){
    fun toEntity() = UniversityResponseEntity(
        id = id,
        name = name,
        longitude = longitude,
        latitude = latitude
    )
}
