package com.hankki.domain.login.repository

import com.hankki.domain.login.entity.request.LoginRequestEntity
import com.hankki.domain.login.entity.response.LoginResponseEntity

interface LoginRepository {
    suspend fun postLogin(
        accessToken: String,
        loginRequest: LoginRequestEntity
    ): Result<LoginResponseEntity>
}
