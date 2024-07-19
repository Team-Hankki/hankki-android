package com.hankki.domain.report.entity.response

data class JogboResponseEntity(
    val id: Long,
    val title: String,
    val imageType: String,
    val details: List<String>,
    val isAdded: Boolean
)
