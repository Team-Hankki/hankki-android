package com.hankki.data.home.dto.response

import com.hankki.domain.home.entity.response.StorePinEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoresPinsDto(
    @SerialName("pins")
    val pins: List<StorePinDto>
) {
    @Serializable
    data class StorePinDto(
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double
    ){
        fun toEntity() = StorePinEntity(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude
        )
    }

    fun toEntity() = pins.map { it.toEntity() }
}
