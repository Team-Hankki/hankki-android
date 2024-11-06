package com.hankki.data.storedetail.service

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.storedetail.request.MenuUpdateRequestDto
import com.hankki.data.storedetail.request.StoreDetailAddMenuRequestDto
import com.hankki.data.storedetail.response.FavoritesResponseDto
import com.hankki.data.storedetail.response.StoreDetailHeartResponseDto
import com.hankki.data.storedetail.response.StoreDetailNicknameResponseDto
import com.hankki.data.storedetail.response.StoreDetailResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreDetailService {
    @GET("/api/v1/stores/{id}")
    suspend fun getStoreDetail(
        @Path("id") id: Long
    ): BaseResponse<StoreDetailResponseDto>

    @POST("/api/v1/stores/{id}/hearts")
    suspend fun postStoreDetailHearts(
        @Path("id") id: Long
    ): BaseResponse<StoreDetailHeartResponseDto>

    @DELETE("/api/v1/stores/{id}/hearts")
    suspend fun deleteStoreDetailHearts(
        @Path("id") id: Long
    ): BaseResponse<StoreDetailHeartResponseDto>

    @GET("api/v1/favorites")
    suspend fun getMyJogbo(
        @Query("candidate") candidate: Long
    ): BaseResponse<FavoritesResponseDto>


    @POST("api/v1/favorites/{favoriteId}/stores/{storeId}")
    suspend fun addStoreAtJogbo(
        @Path("favoriteId") favoriteId: Long,
        @Path("storeId") storeId: Long
    ): CreatedBaseResponse

    @GET("/api/v1/users/me")
    suspend fun getStoreDetailNickname(): BaseResponse<StoreDetailNicknameResponseDto>

    @DELETE("/api/v1/stores/{menuId}")
    suspend fun deleteStoreDetail(
        @Path("menuId") storeId: Long
    )

    @PATCH("/api/v1/{storeId}/menus/{menuId}")
    suspend fun patchMenuUpdate(
        @Path("storeId") storeId: Long,
        @Path("menuId") menuId: Long,
        @Body request: MenuUpdateRequestDto
    ): CreatedBaseResponse

    @DELETE("/api/v1/{storeId}/menus/{menuId}")
    suspend fun deleteMenuItem(
        @Path("storeId") storeId: Long,
        @Path("menuId") menuId: Long,
    )

    @POST("/api/v1/{storeId}/menus/bulk")
    suspend fun postMenus(
        @Path("storeId") storeId: Long,
        @Body menus: List<StoreDetailAddMenuRequestDto>
    ): CreatedBaseResponse
}
