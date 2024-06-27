package com.hankki.data.dummy.repositoryimpl

import com.hankki.data.dummy.datasource.ReqresDataSource
import com.hankki.data.dummy.dto.toResponseUserList
import com.hankki.domain.dummy.entity.response.ReqresUserModel
import com.hankki.domain.dummy.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl @Inject constructor(
    private val reqresDataSource: ReqresDataSource,
) : ReqresRepository {
    override suspend fun getUserList(page: Int): Result<List<ReqresUserModel>> =
        runCatching {
            reqresDataSource.getUserList(page).toResponseUserList()
        }
}
