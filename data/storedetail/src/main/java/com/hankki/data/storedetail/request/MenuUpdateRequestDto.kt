package com.hankki.data.storedetail.request

import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuUpdateRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int
)

fun MenuUpdateRequestEntity.toDto() = MenuUpdateRequestDto (
    name = name,
    price = price
)