package com.hankki.data.storedetail.repositoryImpl

import com.hankki.data.storedetail.datasource.StoreDetailDataSource
import com.hankki.data.storedetail.request.toDto
import com.hankki.domain.storedetail.entity.JogboResponseEntity
import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailMenuAddRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailNicknameEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import timber.log.Timber
import javax.inject.Inject

class StoreDetailRepositoryImpl @Inject constructor(
    private val storeDetailDataSource: StoreDetailDataSource,
) : StoreDetailRepository {
    override suspend fun getStoreDetail(
        id: Long,
    ): Result<StoreDetailResponseEntity> =
        runCatching {
            storeDetailDataSource.getStoreDetail(
                id,
            ).data.toStoreDetailResponseEntity()
        }

    override suspend fun postStoreDetailHearts(
        storeId: Long,
    ): Result<StoreDetailHeartsResponseEntity> =
        runCatching {
            storeDetailDataSource.postStoreDetailHearts(
                storeId
            ).data.toStoreDetailHeartsResponseEntity()
        }

    override suspend fun deleteStoreDetailHearts(
        storeId: Long,
    ): Result<StoreDetailHeartsResponseEntity> =
        runCatching {
            storeDetailDataSource.deleteStoreDetailHearts(
                storeId
            ).data.toStoreDetailHeartsResponseEntity()
        }

    override suspend fun getFavorites(storeId: Long): Result<List<JogboResponseEntity>> =
        runCatching {
            storeDetailDataSource.getMyJogbo(storeId).data.toEntity()
        }

    override suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): Result<Unit> =
        runCatching {
            storeDetailDataSource.addStoreAtJogbo(favoriteId, storeId)
        }

    override suspend fun getStoreDetailNickname(): Result<StoreDetailNicknameEntity> =
        runCatching {
            storeDetailDataSource.getStoreDetailNickname().data.toStoreDetailNicknameEntity()
        }

    override suspend fun deleteStoreDetail(storeId: Long): Result<Unit> =
        runCatching {
            storeDetailDataSource.deleteStoreDetail(storeId)
        }

    override suspend fun putUpdateMenu(
        storeId: Long,
        menuId: Long,
        request: MenuUpdateRequestEntity
    ): Result<Unit> {
        return runCatching {
            storeDetailDataSource.patchMenuUpdate(storeId, menuId, request.toDto())
        }
    }

    override suspend fun deleteMenuItem(storeId: Long, menuId: Long): Result<Unit> {
        return runCatching {
            storeDetailDataSource.deleteMenuItem(storeId, menuId)
        }
    }

    override suspend fun postMenus(
        storeId: Long,
        menus: List<StoreDetailMenuAddRequestEntity>
    ): Result<Unit> = runCatching {
        storeDetailDataSource.postMenus(
            storeId = storeId,
            menus = menus.map { it.toDto() }
        )
    }
}

