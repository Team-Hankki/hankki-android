package com.hankki.feature.report.main

sealed class ReportSideEffect {
    data class NavigateReportFinish(
        val count: Long,
        val storeName: String,
        val storeId: Long
    ) : ReportSideEffect()
}
