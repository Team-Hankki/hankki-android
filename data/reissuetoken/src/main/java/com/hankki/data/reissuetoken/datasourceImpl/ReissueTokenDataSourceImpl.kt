package com.hankki.data.reissuetoken.datasourceImpl

import com.hankki.core.network.BaseResponse
import com.hankki.data.reissuetoken.datasource.ReissueTokenDataSource
import com.hankki.data.reissuetoken.response.ReissueTokenResponseDto
import com.hankki.data.reissuetoken.service.ReissueTokenService
import javax.inject.Inject

class ReissueTokenDataSourceImpl @Inject constructor(
    private val reissueTokenSercive: ReissueTokenService
) : ReissueTokenDataSource {
    override suspend fun postReissueToken(
        authorization: String
    ): BaseResponse<ReissueTokenResponseDto> =
        reissueTokenSercive.postReissueToken(authorization)
}