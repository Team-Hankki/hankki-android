package com.hankki.domain.storedetail.repository

import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity

interface  StoreDetailRepository {
    suspend fun getStoreDetail(
        id: Long
    ): Result<StoreDetailResponseEntity>
}
