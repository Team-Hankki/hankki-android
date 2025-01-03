package com.hankki.data.my.service

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.my.dto.request.JogbosRequestDto
import com.hankki.data.my.dto.request.NewJogboDto
import com.hankki.data.my.dto.response.IsJogboOwnerResponseDto
import com.hankki.data.my.dto.response.JogboDetailDto
import com.hankki.data.my.dto.response.LikedStoreResponseDto
import com.hankki.data.my.dto.response.MyJogboDto
import com.hankki.data.my.dto.response.StoreDto
import com.hankki.data.my.dto.response.UserInformationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MyService {
    @GET("/api/v1/users/me")
    suspend fun getUserInformation(): BaseResponse<UserInformationDto>

    @GET("/api/v1/users/me/favorites")
    suspend fun getMyJogboInformation(): BaseResponse<MyJogboDto>

    @POST("/api/v1/favorites")
    suspend fun postNewJogbo(
        @Body body: NewJogboDto,
    ): CreatedBaseResponse

    @GET("/api/v1/favorites/{favoriteId}")
    suspend fun getJogboDetail(
        @Path("favoriteId") favoriteId: Long,
    ): BaseResponse<JogboDetailDto>

    @GET("/api/v1/users/me/stores/hearts")
    suspend fun getLikedStore(): BaseResponse<StoreDto>

    @GET("/api/v1/users/me/stores/reports")
    suspend fun getReportedStore(): BaseResponse<StoreDto>

    @PATCH("/api/v1/auth/logout")
    suspend fun patchLogout(): CreatedBaseResponse

    @DELETE("/api/v1/auth/withdraw")
    suspend fun deleteWithdraw(): CreatedBaseResponse

    @DELETE("/api/v1/favorites/{favoriteId}/stores/{storeId}")
    suspend fun deleteJogboStore(
        @Path("favoriteId") favoriteId: Long,
        @Path("storeId") storeId: Long,
    )

    @POST("api/v1/favorites/batch-delete")
    suspend fun deleteJogboStores(
        @Body body: JogbosRequestDto,
    )

    @POST("api/v1/stores/{id}/hearts")
    suspend fun postLikeStore(
        @Path("id") storeId: Long,
    ): BaseResponse<LikedStoreResponseDto>

    @DELETE("/api/v1/stores/{id}/hearts")
    suspend fun deleteLikeStore(
        @Path("id") storeId: Long,
    ): BaseResponse<LikedStoreResponseDto>

    @GET("/api/v1/favorites/shared/{favoriteId}/ownership")
    suspend fun getIsJogboOwner(
        @Path("favoriteId") favoriteId: Long,
    ): BaseResponse<IsJogboOwnerResponseDto>

    @POST("/api/v1/favorites/shared/{favoriteId}")
    suspend fun postSharedJogbo(
        @Path("favoriteId") favoriteId: Long,
        @Body body: NewJogboDto,
        ): CreatedBaseResponse
}
