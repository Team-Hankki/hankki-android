package com.hankki.data.storedetail.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.storedetail.response.StoreDetailHeartResponseDto
import com.hankki.data.storedetail.response.StoreDetailResponseDto

interface StoreDetailDataSource {
    suspend fun getStoreDetail(
        id:Long
    ): BaseResponse<StoreDetailResponseDto>

    suspend fun postStoreDetailHearts(
        id: Long
    ): BaseResponse<StoreDetailHeartResponseDto>

    suspend fun deleteStoreDetailHearts(
        id: Long
    ): BaseResponse<StoreDetailHeartResponseDto>
}
