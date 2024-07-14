package com.hankki.feature.report.finish

sealed class ReportFinishSideEffect {
    data class navigateToStoreDetail(
        val storeId: Long
    ) : ReportFinishSideEffect()

    data object navigateToHome : ReportFinishSideEffect()
}
