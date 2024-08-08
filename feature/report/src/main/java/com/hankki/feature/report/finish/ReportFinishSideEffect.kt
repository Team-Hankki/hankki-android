package com.hankki.feature.report.finish

sealed class ReportFinishSideEffect {
    data class NavigateToStoreDetail(
        val storeId: Long,
    ) : ReportFinishSideEffect()

    data object NavigateToHome : ReportFinishSideEffect()
    data class ShowSnackBar(val message: String, val jogboId: Long) : ReportFinishSideEffect()
}
