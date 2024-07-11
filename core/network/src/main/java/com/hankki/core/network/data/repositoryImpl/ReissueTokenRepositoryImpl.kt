package com.hankki.core.network.data.repositoryImpl

import com.hankki.core.network.data.datasource.ReissueTokenDataSource
import com.hankki.core.network.domain.entity.ReissueTokenResponseEntity
import com.hankki.core.network.domain.repository.ReissueTokenRepository
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
