package com.hankki.data.report.dto.request

import com.hankki.domain.report.entity.request.ReportStoreRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportStoreRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("category")
    val category: String,
    @SerialName("address")
    val address: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("universityId")
    val universityId: Long,
    @SerialName("menus")
    val menus: List<MenuDto>,
) {
    @Serializable
    data class MenuDto(
        @SerialName("name")
        val name: String,
        @SerialName("price")
        val price: String,
    )
}

fun ReportStoreRequestEntity.toDto() = ReportStoreRequestDto(
    name = name,
    category = category,
    address = address,
    latitude = latitude,
    longitude = longitude,
    universityId = universityId,
    menus = menus.map {
        ReportStoreRequestDto.MenuDto(
            name = it.name,
            price = it.price
        )
    }
)
