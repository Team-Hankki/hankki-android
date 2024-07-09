package com.hankki.data.login.datasourceImpl

import com.hankki.data.login.datasource.LoginDataSource
import com.hankki.data.login.dto.BaseResponse
import com.hankki.data.login.dto.request.LoginRequestDto
import com.hankki.data.login.dto.response.LoginResponseDto
import com.hankki.data.login.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : LoginDataSource {
    override suspend fun postLogin(
        accessToken: String,
        platform: LoginRequestDto
    ): BaseResponse<LoginResponseDto> =
        loginService.postLogin(accessToken, platform)
}
