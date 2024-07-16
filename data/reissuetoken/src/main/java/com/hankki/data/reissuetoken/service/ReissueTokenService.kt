package com.hankki.data.reissuetoken.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.reissuetoken.response.ReissueTokenResponseDto
import retrofit2.http.Header
import retrofit2.http.POST

interface ReissueTokenService {
    @POST("/api/v1/auth/reissue")
    suspend fun postReissueToken(
        @Header("Authorization") accessToken: String
    ): BaseResponse<ReissueTokenResponseDto>
}
