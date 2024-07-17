package com.hankki.data.universityselection.dto.request

import com.hankki.domain.universityselection.entity.UniversitySelectionRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UniversitySelectionRequestDto(
    @SerialName("universityId")
    val universityId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("latitude")
    val latitude: Double
)

fun UniversitySelectionRequestEntity.toDto() = UniversitySelectionRequestDto (
    universityId = universityId,
    name = name,
    longitude = longitude,
    latitude = latitude
)
