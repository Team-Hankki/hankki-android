package com.hankki.data.report.dto.request

import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidateStoreRequestDto(
    @SerialName("universityId")
    val universityId: Long,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("storeName")
    val storeName: String
)

fun ValidateStoreRequestEntity.toDto() = ValidateStoreRequestDto(
    universityId = universityId,
    latitude = latitude,
    longitude = longitude,
    storeName = storeName
)
