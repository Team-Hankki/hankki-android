package com.hankki.core.network.data.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.data.response.ReissueTokenResponseDto

interface ReissueTokenDataSource {
    suspend fun postReissueToken(
        authorization: String
    ): BaseResponse<ReissueTokenResponseDto>
}
