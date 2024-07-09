package com.hankki.data.login.datasource

import com.hankki.data.login.dto.BaseResponse
import com.hankki.data.login.dto.request.LoginRequestDto
import com.hankki.data.login.dto.response.LoginResponseDto

interface LoginDataSource {
    suspend fun postLogin(
        accessToken: String,
        platform: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>
}
