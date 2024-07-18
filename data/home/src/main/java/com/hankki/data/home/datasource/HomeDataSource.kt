package com.hankki.data.home.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse
import com.hankki.data.home.dto.response.FavoritesResponseDto
import com.hankki.data.home.dto.response.StoreThumbnailResponseDto
import com.hankki.data.home.dto.response.StoresPinsDto
import com.hankki.data.home.dto.response.StoresResponseDto
import com.hankki.data.home.dto.response.UniversityResponseDto

interface HomeDataSource {
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>
    suspend fun getCategories(): BaseResponse<CategoriesResponse>
    suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse>
    suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse>
    suspend fun getStores(
        universityId: Long?,
        storeCategory: String?,
        priceCategory: String?,
        sortOption: String?,
    ): BaseResponse<StoresResponseDto>
    suspend fun getStoresPins(
        universityId: Long?,
        storeCategory: String?,
        priceCategory: String?,
        sortOption: String?,
    ): BaseResponse<StoresPinsDto>
    suspend fun getStoreThumbnail(storeId: Long): BaseResponse<StoreThumbnailResponseDto>
    suspend fun getMyJogbo(storeId: Long): BaseResponse<FavoritesResponseDto>
    suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): CreatedBaseResponse
}
