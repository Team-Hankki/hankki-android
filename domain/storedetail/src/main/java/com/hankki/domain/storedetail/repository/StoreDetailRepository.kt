package com.hankki.domain.storedetail.repository

import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity

interface StoreDetailRepository {
    suspend fun getStoreDetail(
        id: Long
    ): Result<StoreDetailResponseEntity>

    suspend fun postStoreDetailHearts(
        storeId: Long
    ): Result<StoreDetailHeartsResponseEntity>

    suspend fun deleteStoreDetailHearts(
        storeId: Long
    ): Result<StoreDetailHeartsResponseEntity>
}
