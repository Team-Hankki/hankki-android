package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.CountEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountResponseDto(
    @SerialName("count")
    val count: Long
){
    fun toEntity() = CountEntity(count = count)
}