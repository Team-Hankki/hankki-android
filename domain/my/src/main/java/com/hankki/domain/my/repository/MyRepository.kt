package com.hankki.domain.my.repository

import com.hankki.domain.my.entity.request.NewJogboEntity
import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.domain.my.entity.response.UserInformationEntity

interface MyRepository {
    suspend fun getUserInformation() : Result<UserInformationEntity>
    suspend fun getMyJogboList() : Result<List<MyJogboEntity>>
    suspend fun createNewJogbo(body: NewJogboEntity): Result<Unit>
    suspend fun patchLogout(): Result<Unit>
}
