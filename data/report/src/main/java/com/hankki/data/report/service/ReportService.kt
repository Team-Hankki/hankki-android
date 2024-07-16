package com.hankki.data.report.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto

import retrofit2.http.GET
import retrofit2.http.Query

interface ReportService {
    @GET("api/v1/locations")
    suspend fun getStoreLocation(
        @Query("query") query: String,
    ): BaseResponse<LocationsResponseDto>

    @GET("api/v1/reports/count")
    suspend fun getReportsCount(): BaseResponse<CountResponseDto>
}
