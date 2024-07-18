package com.hankki.data.my.repositoryimpl

import com.hankki.data.my.datasource.MyDataSource
import com.hankki.data.my.dto.request.toDto
import com.hankki.data.my.dto.response.toEntity
import com.hankki.domain.my.entity.request.NewJogboEntity
import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.domain.my.entity.response.StoreEntity
import com.hankki.domain.my.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {
    override suspend fun getUserInformation() = runCatching {
        myDataSource.getUserInformation().data.toEntity()
    }

    override suspend fun getMyJogboList(): Result<List<MyJogboEntity>> = runCatching {
        myDataSource.getMyJogboList().data.toEntity()
    }

    override suspend fun createNewJogbo(body: NewJogboEntity): Result<Unit> = runCatching {
        myDataSource.postNewJogbo(body.toDto())
    }

    override suspend fun getJogboDetail(favoriteId: Long): Result<MyJogboDetailEntity> =
        runCatching {
            myDataSource.getJogboDetail(favoriteId).data.toEntity()
        }

    override suspend fun getLikedStore(): Result<List<StoreEntity>> = runCatching {
        myDataSource.getLikedStore().data.toEntity()
    }

    override suspend fun getReportedStore(): Result<List<StoreEntity>> = runCatching {
        myDataSource.getReportedStore().data.toEntity()
    }

    override suspend fun patchLogout(): Result<Unit> = kotlin.runCatching {
        myDataSource.patchLogout()
    }
}
