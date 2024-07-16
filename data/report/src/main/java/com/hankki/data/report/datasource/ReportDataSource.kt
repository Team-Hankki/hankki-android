package com.hankki.data.report.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.report.dto.LocationsResponseDto

interface ReportDataSource {
    suspend fun getLocations(query: String): BaseResponse<LocationsResponseDto>
}
