package com.hankki.domain.reissuetoken.repository

import com.hankki.domain.reissuetoken.entity.ReissueTokenResponseEntity

interface ReissueTokenRepository {
    suspend fun postReissueToken(
        refreshToken: String,
    ): Result<ReissueTokenResponseEntity>
}
