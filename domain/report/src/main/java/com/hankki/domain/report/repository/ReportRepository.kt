package com.hankki.domain.report.repository

import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.domain.report.entity.CountEntity
import com.hankki.domain.report.entity.LocationEntity
import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity

interface ReportRepository {
    suspend fun getStoreLocation(search: String): Result<List<LocationEntity>>
    suspend fun getReportCount(): Result<CountEntity>
    suspend fun getStoreValidate(body: ValidateStoreRequestEntity): Result<Unit>
    suspend fun getCategories(): Result<List<CategoryEntity>>
}
