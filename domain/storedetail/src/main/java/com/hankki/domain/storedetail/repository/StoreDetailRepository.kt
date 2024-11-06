package com.hankki.domain.storedetail.repository

import com.hankki.domain.storedetail.entity.JogboResponseEntity
import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailMenuAddRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailNicknameEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity

interface StoreDetailRepository {
    suspend fun getStoreDetail(
        id: Long,
    ): Result<StoreDetailResponseEntity>

    suspend fun postStoreDetailHearts(
        storeId: Long,
    ): Result<StoreDetailHeartsResponseEntity>

    suspend fun deleteStoreDetailHearts(
        storeId: Long,
    ): Result<StoreDetailHeartsResponseEntity>

    suspend fun getFavorites(storeId: Long): Result<List<JogboResponseEntity>>

    suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): Result<Unit>

    suspend fun getStoreDetailNickname(): Result<StoreDetailNicknameEntity>

    suspend fun deleteStoreDetail(storeId: Long) : Result<Unit>

    suspend fun putUpdateMenu(storeId: Long, menuId: Long, request: MenuUpdateRequestEntity): Result<Unit>

    suspend fun postMenus(
        storeId: Long,
        menus: List<StoreDetailMenuAddRequestEntity>
    ): Result<Unit>

    suspend fun deleteMenuItem(storeId: Long, menuId: Long): Result<Unit>
}
