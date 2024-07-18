package com.hankki.data.my.repositoryimpl

import com.hankki.data.my.datasource.MyDataSource
import com.hankki.data.my.dto.response.toEntity
import com.hankki.domain.my.entity.MyRepository
import com.hankki.domain.my.entity.response.MyJogboEntity
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {
    override suspend fun getUserInformation() = runCatching {
        myDataSource.getUserInformation().data.toEntity()
    }
    override suspend fun getMyJogboList() :Result<List<MyJogboEntity>> = runCatching {
        myDataSource.getMyJogboList().data.toEntity()
    }
}
