package com.hankki.data.dummy.service

import com.hankki.data.dummy.dto.RequreUserList
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("users")
    suspend fun getUserInfo(@Query("page") page: Int): RequreUserList
}
