package com.hankki.data.report.service

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.report.dto.request.ValidateStoreRequestDto
import com.hankki.data.report.dto.response.CategoriesResponseDto
import com.hankki.data.report.dto.response.CountResponseDto
import com.hankki.data.report.dto.response.FavoritesResponseDto
import com.hankki.data.report.dto.response.GeneratedStoreResponseDto
import com.hankki.data.report.dto.response.LocationsResponseDto
import com.hankki.data.report.dto.response.UniversityResponseDto
import com.hankki.data.report.dto.response.UserInfoResponseDto
import com.hankki.domain.report.entity.response.UserInfoResponseEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportService {
    @GET("api/v1/locations")
    suspend fun getStoreLocation(
        @Query("query") query: String,
    ): BaseResponse<LocationsResponseDto>

    @GET("api/v1/reports/count")
    suspend fun getReportsCount(): BaseResponse<CountResponseDto>

    @POST("api/v1/stores/validate")
    suspend fun getStoreValidate(
        @Body body: ValidateStoreRequestDto,
    ): CreatedBaseResponse

    @GET("api/v1/stores/categories")
    suspend fun getCategories(): BaseResponse<CategoriesResponseDto>

    @Multipart
    @POST("api/v1/stores")
    suspend fun postReport(
        @Part image: MultipartBody.Part?,
        @Part("request") request: RequestBody,
    ): BaseResponse<GeneratedStoreResponseDto>

    @GET("api/v1/users/me/university")
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>

    @GET("/api/v1/users/me")
    suspend fun getUserInfo() : BaseResponse<UserInfoResponseDto>

    @GET("api/v1/favorites")
    suspend fun getMyJogbo(
        @Query("candidate") candidate: Long
    ): BaseResponse<FavoritesResponseDto>

    @POST("api/v1/favorites/{favoriteId}/stores/{storeId}")
    suspend fun addStoreAtJogbo(
        @Path("favoriteId") favoriteId: Long,
        @Path("storeId") storeId: Long
    ): CreatedBaseResponse
}
