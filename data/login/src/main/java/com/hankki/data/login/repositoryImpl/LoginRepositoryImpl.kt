package com.hankki.data.login.repositoryImpl

import com.hankki.data.login.datasource.LoginDataSource
import com.hankki.data.login.dto.request.toLoginRequestDto
import com.hankki.domain.login.entity.request.LoginRequestModel
import com.hankki.domain.login.entity.response.LoginResponseModel
import com.hankki.domain.login.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun postLogin(
        Authorization: String,
        requestLoginModel: LoginRequestModel,
    ): Result<LoginResponseModel> {
        return runCatching {
            loginDataSource.postLogin(
                Authorization,
                requestLoginModel.toLoginRequestDto(),
            ).data.toLoginModel()
        }
    }
}