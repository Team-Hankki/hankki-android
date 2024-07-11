package com.hankki.core.network.data.datasourceImpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.data.datasource.ReissueTokenDataSource
import com.hankki.core.network.data.response.ReissueTokenResponseDto
import com.hankki.core.network.data.service.ReissueTokenService
import javax.inject.Inject

class ReissueTokenDataSourceImpl @Inject constructor(
    private val reissueTokenSercive: ReissueTokenService
) : ReissueTokenDataSource {
    override suspend fun postReissueToken(
        accessToken: String
    ): BaseResponse<ReissueTokenResponseDto> =
        reissueTokenSercive.postReissueToken(accessToken)
}
