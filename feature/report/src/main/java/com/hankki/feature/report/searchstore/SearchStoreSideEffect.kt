package com.hankki.feature.report.searchstore

sealed class SearchStoreSideEffect {
    data class ValidateUniversitySuccess(
        val latitude: String,
        val longitude: String,
        val location: String,
        val address: String,
    ) : SearchStoreSideEffect()

    data class NavigateToStoreDetail(val storeId: Long) : SearchStoreSideEffect()
}
