package com.hankki.data.storedetail.datasourceImpl

import com.hankki.core.network.BaseResponse
import com.hankki.data.storedetail.datasource.StoreDetailDataSource
import com.hankki.data.storedetail.response.StoreDetailResponseDto
import com.hankki.data.storedetail.service.StoreDetailService
import javax.inject.Inject

class StoreDetailDataSourceImpl @Inject constructor (
    private val storeDetailService: StoreDetailService
) : StoreDetailDataSource {
    override suspend fun getStoreDetail(
        id: Long
    ) : BaseResponse<StoreDetailResponseDto> =
        storeDetailService.getStoreDetail(id)
}