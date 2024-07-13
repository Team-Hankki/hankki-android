package com.hankki.data.reissuetoken.repositoryImpl

import com.hankki.data.reissuetoken.datasource.ReissueTokenDataSource
import com.hankki.domain.reissuetoken.entity.ReissueTokenResponseEntity
import com.hankki.domain.reissuetoken.repository.ReissueTokenRepository
import javax.inject.Inject

class ReissueTokenRepositoryImpl @Inject constructor(
    private val reissueTokenDataSource: ReissueTokenDataSource
) : ReissueTokenRepository {
    override suspend fun postReissueToken(
        authorization: String,
    ): Result<ReissueTokenResponseEntity> =
        runCatching {
            reissueTokenDataSource.postReissueToken(
                authorization,
            ).data.toReissueTokenResponseEntity()
        }
}
