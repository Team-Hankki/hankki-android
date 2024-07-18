package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.response.GeneratedStoreResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneratedStoreResponseDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
) {
    fun toEntity() = GeneratedStoreResponseEntity(
        id = id,
        name = name
    )
}
