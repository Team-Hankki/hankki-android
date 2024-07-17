package com.hankki.data.report.datasourceimpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.report.datasource.ReportDataSource
import com.hankki.data.report.dto.request.ValidateStoreRequestDto
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto
import com.hankki.data.report.service.ReportService
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportDataSource {
    override suspend fun getLocations(query: String): BaseResponse<LocationsResponseDto> =
        reportService.getStoreLocation(query)

    override suspend fun getReportsCount(): BaseResponse<CountResponseDto> =
        reportService.getReportsCount()

    override suspend fun getStoreValidate(body: ValidateStoreRequestDto): CreatedBaseResponse =
        reportService.getStoreValidate(body)
}
