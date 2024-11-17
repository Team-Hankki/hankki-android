package com.hankki.data.storedetail.request

import com.hankki.domain.storedetail.entity.StoreDetailMenuAddRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailAddMenuRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Int
)

fun StoreDetailMenuAddRequestEntity.toDto() = StoreDetailAddMenuRequestDto(
    name = name,
    price = price
)