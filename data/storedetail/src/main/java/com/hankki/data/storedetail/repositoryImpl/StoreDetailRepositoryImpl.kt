package com.hankki.data.storedetail.repositoryImpl

import com.hankki.data.storedetail.datasource.StoreDetailDataSource
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import javax.inject.Inject

class StoreDetailRepositoryImpl @Inject constructor(
    private val storeDetailDataSource: StoreDetailDataSource
) : StoreDetailRepository {
    override suspend fun getStoreDetail(
        id: Long,
    ): Result<StoreDetailResponseEntity> =
        runCatching {
            storeDetailDataSource.getStoreDetail(
                id,
            ).data.toStoreDetailResponseEntity()
        }
}
