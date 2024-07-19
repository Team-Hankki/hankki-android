package com.hankki.data.my.dto.response

import com.hankki.domain.my.entity.response.LikedStoreResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikedStoreResponseDto(
    @SerialName("storeId")
    val storeId: Long,
    @SerialName("count")
    val count: Int,
    @SerialName("isHearted")
    val isHearted: Boolean
) {
    fun toEntity() = LikedStoreResponseEntity(
        storeId = storeId,
        count = count,
        isHearted = isHearted
    )
}
