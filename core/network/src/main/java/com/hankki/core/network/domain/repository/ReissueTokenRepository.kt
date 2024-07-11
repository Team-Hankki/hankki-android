package com.hankki.core.network.domain.repository

import com.hankki.core.network.domain.entity.ReissueTokenResponseEntity

interface ReissueTokenRepository {
    suspend fun postReissueToken(
        authorization: String,
    ): Result<ReissueTokenResponseEntity>
}
