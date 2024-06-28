package com.hankki.data.dummy.datasource

import com.hankki.data.dummy.dto.RequreUserList

interface ReqresDataSource {
    suspend fun getUserList(page: Int): RequreUserList
}
