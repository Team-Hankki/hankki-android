package com.hankki.data.report.datasourceimpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.report.datasource.ReportDataSource
import com.hankki.data.report.dto.request.ValidateStoreRequestDto
import com.hankki.data.report.dto.response.CategoriesResponseDto
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.FavoritesResponseDto
import com.hankki.data.report.dto.response.GeneratedStoreResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto
import com.hankki.data.report.dto.response.UniversityResponseDto
import com.hankki.data.report.dto.response.UserInfoResponseDto
import com.hankki.data.report.service.ReportService
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override suspend fun getCategories(): BaseResponse<CategoriesResponseDto> =
        reportService.getCategories()

    override suspend fun postReport(
        image: MultipartBody.Part?,
        request: RequestBody,
    ): BaseResponse<GeneratedStoreResponseDto> =
        reportService.postReport(image, request)

    override suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto> =
        reportService.getMyUniversity()

    override suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto> =
        reportService.getUserInfo()

    override suspend fun getMyJogbo(storeId: Long): BaseResponse<FavoritesResponseDto> =
        reportService.getMyJogbo(storeId)

    override suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): CreatedBaseResponse =
        reportService.addStoreAtJogbo(favoriteId, storeId)
}
