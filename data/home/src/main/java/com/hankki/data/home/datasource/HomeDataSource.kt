package com.hankki.data.home.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse
import com.hankki.data.home.dto.response.UniversityResponseDto

interface HomeDataSource {
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>
    suspend fun getCategories(): BaseResponse<CategoriesResponse>
    suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse>
    suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse>
}
