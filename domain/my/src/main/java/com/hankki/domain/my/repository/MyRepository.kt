package com.hankki.domain.my.repository

import com.hankki.domain.my.entity.request.NewJogboEntity
import com.hankki.domain.my.entity.response.LikedStoreResponseEntity
import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.domain.my.entity.response.StoreEntity
import com.hankki.domain.my.entity.response.UserInformationEntity

interface MyRepository {
    suspend fun getUserInformation(): Result<UserInformationEntity>
    suspend fun getMyJogboList(): Result<List<MyJogboEntity>>
    suspend fun createNewJogbo(body: NewJogboEntity): Result<Unit>
    suspend fun getJogboDetail(favoriteId: Long): Result<MyJogboDetailEntity>
    suspend fun getLikedStore(): Result<List<StoreEntity>>
    suspend fun getReportedStore(): Result<List<StoreEntity>>
    suspend fun patchLogout(): Result<Unit>
    suspend fun deleteWithdraw(): Result<Unit>
    suspend fun deleteJogboStore(favoriteId: Long, storeId: Long): Result<Unit>
    suspend fun deleteJogboStores(body: List<Long>): Result<Unit>
    suspend fun unLikeStore(storeId: Long): Result<LikedStoreResponseEntity>
}
