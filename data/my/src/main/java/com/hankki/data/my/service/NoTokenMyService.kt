package com.hankki.data.my.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.my.dto.response.JogboDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NoTokenMyService {
    @GET("/api/v1/favorites/shared/{favoriteId}")
    suspend fun getSharedJogboDetail(
        @Path("favoriteId") favoriteId: Long,
    ): BaseResponse<JogboDetailDto>
}
