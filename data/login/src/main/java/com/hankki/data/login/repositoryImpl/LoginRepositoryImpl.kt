package com.hankki.data.login.repositoryImpl

import com.hankki.data.login.datasource.LoginDataSource
import com.hankki.data.login.dto.request.toLoginRequestDto
import com.hankki.domain.login.entity.request.LoginRequestEntity
import com.hankki.domain.login.entity.response.LoginResponseEntity
import com.hankki.domain.login.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun postLogin(
        accessToken: String,
        loginRequest: LoginRequestEntity,
    ): Result<LoginResponseEntity> = runCatching {
        loginDataSource.postLogin(
            accessToken,
            loginRequest.toLoginRequestDto(),
        ).data.toLoginEntity()
    }
}
