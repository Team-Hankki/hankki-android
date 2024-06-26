package com.hankki.data.dummy.datasourceimpl

import com.hankki.data.dummy.datasource.ReqresDataSource
import com.hankki.data.dummy.dto.RequreUserList
import com.hankki.data.dummy.service.ReqresService
import javax.inject.Inject

class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService,
) : ReqresDataSource {
    override suspend fun getUserList(page: Int): RequreUserList =
        reqresService.getUserInfo(page)
}
