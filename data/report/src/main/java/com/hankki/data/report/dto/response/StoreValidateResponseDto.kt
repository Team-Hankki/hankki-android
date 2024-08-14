package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.response.StoreValidateResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreValidateResponseDto(
    @SerialName("id")
    val id: Long?,
    @SerialName("isRegistered")
    val isRegistered: Boolean,
) {
    fun toEntity() = StoreValidateResponseEntity(
        id = id,
        isRegistered = isRegistered
    )
}
