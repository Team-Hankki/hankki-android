package com.hankki.domain.my.repository

import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.domain.my.entity.response.UserInformationEntity

interface MyRepository {
    suspend fun getUserInformation() : Result<UserInformationEntity>
    suspend fun getMyJogboList() : Result<List<MyJogboEntity>>
}
