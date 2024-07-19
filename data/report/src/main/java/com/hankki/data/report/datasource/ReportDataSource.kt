package com.hankki.data.report.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.report.dto.request.ValidateStoreRequestDto
import com.hankki.data.report.dto.response.CategoriesResponseDto
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.GeneratedStoreResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto
import com.hankki.data.report.dto.response.UniversityResponseDto
import com.hankki.data.report.dto.response.UserInfoResponseDto
import com.hankki.domain.report.entity.response.UserInfoResponseEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReportDataSource {
    suspend fun getLocations(query: String): BaseResponse<LocationsResponseDto>
    suspend fun getReportsCount(): BaseResponse<CountResponseDto>
    suspend fun getStoreValidate(body: ValidateStoreRequestDto): CreatedBaseResponse
    suspend fun getCategories(): BaseResponse<CategoriesResponseDto>
    suspend fun postReport(image: MultipartBody.Part?, request: RequestBody): BaseResponse<GeneratedStoreResponseDto>
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
}
