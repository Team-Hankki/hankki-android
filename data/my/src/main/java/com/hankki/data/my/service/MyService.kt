package com.hankki.data.my.service

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.my.dto.request.NewJogboDto
import com.hankki.data.my.dto.response.MyJogboDto
import com.hankki.data.my.dto.response.UserInformationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MyService {
    @GET("/api/v1/users/me")
    suspend fun getUserInformation() : BaseResponse<UserInformationDto>
    @GET("/api/v1/users/me/favorites")
    suspend fun getMyJogboInformation() : BaseResponse<MyJogboDto>
    @POST("/api/v1/favorites")
    suspend fun postNewJogbo(
        @Body body : NewJogboDto
    ) : CreatedBaseResponse
}
