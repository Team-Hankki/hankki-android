package com.hankki.feature.report.searchstore

sealed class SearchStoreSideEffect {
    data class ValidateUniversitySuccess(
        val latitude: Float,
        val longitude: Float,
        val location: String,
        val address: String,
    ) : SearchStoreSideEffect()

    data class NavigateToStoreDetail(val storeId: Long) : SearchStoreSideEffect()
}
