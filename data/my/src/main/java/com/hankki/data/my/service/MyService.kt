package com.hankki.data.my.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.my.dto.response.UserInformationDto
import retrofit2.http.GET

interface MyService {
    @GET("/api/v1/users/me")
    suspend fun getUserInformation() : BaseResponse<UserInformationDto>
}
