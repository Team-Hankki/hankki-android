package com.hankki.data.storedetail.response

import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class StoreDetailHeartResponseDto(
    @SerialName("storeId")
    val storeId: Long,
    @SerialName("count")
    val count: Int,
    @SerialName("isHearted")
    val isHearted: Boolean
) {
    fun toStoreDetailHeartsResponseEntity() = StoreDetailHeartsResponseEntity(
        storeId = storeId,
        count = count,
        isHearted = isHearted
    )
}
