package com.hankki.data.report.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportOtherSchoolStoreRequestDto(
    @SerialName("storeId")
    val storeId: Long,
    @SerialName("universityId")
    val universityId: Long,
)
