package com.hankki.domain.universityselection.entity

data class UniversitySelectionRequest(
    val universityId: Long,
    val name: String,
    val longitude: Double,
    val latitude: Double
)
