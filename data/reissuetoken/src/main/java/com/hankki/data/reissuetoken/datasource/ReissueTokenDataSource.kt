package com.hankki.data.reissuetoken.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.reissuetoken.response.ReissueTokenResponseDto

interface ReissueTokenDataSource {
    suspend fun postReissueToken(
        refreshtoken: String
    ): BaseResponse<ReissueTokenResponseDto>
}
