package com.hankki.data.storedetail.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.storedetail.response.StoreDetailResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreDetailService {
    @GET("/api/v1/stores/{id}")
    suspend fun getStoreDetail(
        @Path("id") id: Long
    ): BaseResponse<StoreDetailResponseDto>
}
