package com.hankki.data.my.repositoryimpl

import com.hankki.data.my.datasource.MyDataSource
import com.hankki.domain.my.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {
    override suspend fun getUserInformation() = runCatching {
        myDataSource.getUserInformation().data.toEntity()
    }
}
