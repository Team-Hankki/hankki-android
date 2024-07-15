package com.hankki.data.report.repositoryimpl

import com.hankki.data.report.datasource.ReportDataSource
import com.hankki.data.report.dto.toEntity
import com.hankki.domain.report.entity.LocationEntity
import com.hankki.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource,
) : ReportRepository {
    override suspend fun getStoreLocation(search: String): Result<List<LocationEntity>> =
        runCatching {
            reportDataSource.getLocations(search).data.toEntity()
        }
}
