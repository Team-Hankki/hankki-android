package com.hankki.data.universityselection.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.universityselection.dto.request.UniversitySelectionRequestDto
import com.hankki.data.universityselection.dto.response.UniversitySelectionResponseDto

interface UniversitySelectionDataSource {
    suspend fun getUniversitySelection() : BaseResponse<UniversitySelectionResponseDto>
    suspend fun postUniversitySelection(request: UniversitySelectionRequestDto): CreatedBaseResponse
}
