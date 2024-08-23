package com.hankki.domain.report.entity.request

data class ValidateStoreRequestEntity(
    val universityId: Long,
    val latitude: Double,
    val longitude: Double,
    val storeName: String
)
