package com.hankki.domain.dummy.repository

import com.hankki.domain.dummy.entity.response.ReqresUserModel

interface ReqresRepository {
    suspend fun getUserList(page: Int): Result<List<ReqresUserModel>>
}
