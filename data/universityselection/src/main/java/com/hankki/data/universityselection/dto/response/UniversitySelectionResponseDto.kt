package com.hankki.data.universityselection.dto.response

import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversitySelectionResponseDto(
    @SerialName("universities")
    val universities: List<University>
)


@Serializable
data class University(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double
) {
    fun toUniversitySelectionEntity() = UniversitySelectionEntity(
        id = id,
        name = name,
        longitude = longitude,
        latitude = latitude,
    )
}
