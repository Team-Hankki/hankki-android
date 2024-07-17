package com.hankki.data.report.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.report.dto.request.ValidateStoreRequestDto
import com.hankki.data.report.dto.response.CategoriesResponseDto
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto

interface ReportDataSource {
    suspend fun getLocations(query: String): BaseResponse<LocationsResponseDto>
    suspend fun getReportsCount(): BaseResponse<CountResponseDto>
    suspend fun getStoreValidate(body: ValidateStoreRequestDto): CreatedBaseResponse
    suspend fun getCategories(): BaseResponse<CategoriesResponseDto>
}
