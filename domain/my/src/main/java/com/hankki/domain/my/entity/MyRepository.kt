package com.hankki.domain.my.entity

import com.hankki.domain.my.entity.response.UserInformationEntity

interface MyRepository {
    suspend fun getUserInformation() : Result<UserInformationEntity>
}
