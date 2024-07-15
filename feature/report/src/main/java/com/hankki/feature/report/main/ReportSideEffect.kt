package com.hankki.feature.report.main

sealed class ReportSideEffect {
    data class navigateReportFinish(
        val count: Long,
        val storeName: String,
        val storeId: Long
    ) : ReportSideEffect()
}
