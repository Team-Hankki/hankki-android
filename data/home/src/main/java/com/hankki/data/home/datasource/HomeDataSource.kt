package com.hankki.data.home.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse

interface HomeDataSource {
    suspend fun getCategories(): BaseResponse<CategoriesResponse>
    suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse>
    suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse>
}
