package com.hankki.domain.report.repository

import com.hankki.domain.report.entity.LocationEntity

interface ReportRepository {
    suspend fun getStoreLocation(search: String): Result<List<LocationEntity>>
}
