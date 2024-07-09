package com.hankki.domain.login.repository

import com.hankki.domain.login.entity.request.LoginRequestModel
import com.hankki.domain.login.entity.response.LoginResponseModel

interface LoginRepository {
    suspend fun postLogin(
        accessToken: String,
        loginRequest: LoginRequestModel
    ): Result<LoginResponseModel>
}
