package com.hankki.data.login.service

import com.hankki.core.common.utill.BaseResponse
import com.hankki.data.login.dto.request.LoginRequestDto
import com.hankki.data.login.dto.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/auth/login")
    suspend fun postLogin(
        @Header("Authorization") accessToken: String,
        @Body body: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>
}
