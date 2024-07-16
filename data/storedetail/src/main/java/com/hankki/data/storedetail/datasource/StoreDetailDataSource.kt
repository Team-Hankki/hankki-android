package com.hankki.data.storedetail.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.storedetail.response.StoreDetailResponseDto

interface StoreDetailDataSource {
    suspend fun getStoreDetail(
        id:Long
    ): BaseResponse<StoreDetailResponseDto>
}
