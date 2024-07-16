package com.hankki.data.report.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto

interface ReportDataSource {
    suspend fun getLocations(query: String): BaseResponse<LocationsResponseDto>
    suspend fun getReportsCount(): BaseResponse<CountResponseDto>
}
