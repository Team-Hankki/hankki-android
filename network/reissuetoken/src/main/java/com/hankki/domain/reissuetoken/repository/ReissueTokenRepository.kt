package com.hankki.domain.reissuetoken.repository

import com.hankki.domain.reissuetoken.entity.response.ReissueTokenResponseEntity

interface ReissueTokenRepository {
    suspend fun postReissueToken(
        accessToken: String
    ): Result<ReissueTokenResponseEntity>
}