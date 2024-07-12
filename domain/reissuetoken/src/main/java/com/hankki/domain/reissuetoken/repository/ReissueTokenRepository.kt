package com.hankki.domain.reissuetoken.repository

import com.hankki.domain.reissuetoken.entity.ReissueTokenResponseEntity

interface ReissueTokenRepository {
    suspend fun postReissueToken(
        authorization: String,
    ): Result<ReissueTokenResponseEntity>
}
