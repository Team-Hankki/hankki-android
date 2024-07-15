package com.hankki.data.report.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.report.dto.LocationsResponseDto

import retrofit2.http.GET
import retrofit2.http.Query

interface ReportService {
    @GET("api/v1/locations")
    suspend fun getStoreLocation(
        @Query("search") search: String,
    ): BaseResponse<LocationsResponseDto>
}
