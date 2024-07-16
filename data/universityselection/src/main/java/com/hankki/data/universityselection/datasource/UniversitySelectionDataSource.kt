package com.hankki.data.universityselection.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.universityselection.request.UniversitySelectionRequestDto
import com.hankki.data.universityselection.response.University
import com.hankki.data.universityselection.response.UniversitySelectionResponseDto

interface UniversitySelectionDataSource {
    suspend fun getUniversitySelection() : BaseResponse<UniversitySelectionResponseDto>
    suspend fun postUniversitySelection(request: UniversitySelectionRequestDto): BaseResponse<University>
}
