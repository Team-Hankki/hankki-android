package com.hankki.data.universityselection.request

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