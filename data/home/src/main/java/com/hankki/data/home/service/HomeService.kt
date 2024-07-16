package com.hankki.data.home.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse
import com.hankki.data.home.dto.response.UniversityResponseDto
import retrofit2.http.GET

interface HomeService {
    @GET("api/v1/users/me/university")
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>

    @GET("api/v1/stores/categories")
    suspend fun getCategories(): BaseResponse<CategoriesResponse>

    @GET("api/v1/stores/price-categories")
    suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse>

    @GET("api/v1/stores/sort-options")
    suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse>
}
