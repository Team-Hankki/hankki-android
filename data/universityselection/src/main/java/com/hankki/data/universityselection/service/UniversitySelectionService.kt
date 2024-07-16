package com.hankki.data.universityselection.service

import com.hankki.core.network.BaseResponse
import com.hankki.data.universityselection.request.UniversitySelectionRequestDto
import com.hankki.data.universityselection.response.University
import com.hankki.data.universityselection.response.UniversitySelectionResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UniversitySelectionService {
    @GET("/api/v1/universities")
    suspend fun getUniversitySelection(): BaseResponse<UniversitySelectionResponseDto>

    @POST("/api/v1/users/me/university")
    suspend fun postUniversitySelection(@Body request: UniversitySelectionRequestDto): BaseResponse<University>
}
