package com.hankki.data.home.datasourcimpl

import com.hankki.core.network.BaseResponse
import com.hankki.data.home.datasource.HomeDataSource
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse
import com.hankki.data.home.service.HomeService
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeDataSource {
    override suspend fun getCategories(): BaseResponse<CategoriesResponse> = homeService.getCategories()

    override suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse> = homeService.getPriceCategories()

    override suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse> = homeService.getSortCategories()
}
