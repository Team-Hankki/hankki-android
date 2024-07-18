package com.hankki.data.home.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.home.dto.CategoriesResponse
import com.hankki.data.home.dto.PriceCategoriesResponse
import com.hankki.data.home.dto.SortCategoriesResponse
import com.hankki.data.home.dto.response.FavoritesResponseDto
import com.hankki.data.home.dto.response.StoreThumbnailResponseDto
import com.hankki.data.home.dto.response.StoresPinsDto
import com.hankki.data.home.dto.response.StoresResponseDto
import com.hankki.data.home.dto.response.UniversityResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("api/v1/users/me/university")
    suspend fun getMyUniversity(): BaseResponse<UniversityResponseDto>

    @GET("api/v1/stores/categories")
    suspend fun getCategories(): BaseResponse<CategoriesResponse>

    @GET("api/v1/stores/price-categories")
    suspend fun getPriceCategories(): BaseResponse<PriceCategoriesResponse>

    @GET("api/v1/stores/sort-options")
    suspend fun getSortCategories(): BaseResponse<SortCategoriesResponse>

    @GET("api/v1/stores")
    suspend fun getStores(
        @Query("universityId") universityId: Long?,
        @Query("storeCategory") storeCategory: String?,
        @Query("priceCategory") priceCategory: String?,
        @Query("sortOption") sortOption: String?
    ): BaseResponse<StoresResponseDto>

    @GET("api/v1/stores/pins")
    suspend fun getStoresPins(
        @Query("universityId") universityId: Long?,
        @Query("storeCategory") storeCategory: String?,
        @Query("priceCategory") priceCategory: String?,
        @Query("sortOption") sortOption: String?
    ): BaseResponse<StoresPinsDto>

    @GET("api/v1/stores/{id}/thumbnail")
    suspend fun getStoreThumbnail(
        @Path("id") storeId: Long
    ): BaseResponse<StoreThumbnailResponseDto>

    @GET("api/v1/favorites")
    suspend fun getMyJogbo(
        @Query("candidate") candidate: Long
    ): BaseResponse<FavoritesResponseDto>
}
