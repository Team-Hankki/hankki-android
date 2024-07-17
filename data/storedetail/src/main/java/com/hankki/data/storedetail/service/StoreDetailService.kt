package com.hankki.data.storedetail.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.storedetail.response.StoreDetailHeartResponseDto
import com.hankki.data.storedetail.response.StoreDetailResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
}
