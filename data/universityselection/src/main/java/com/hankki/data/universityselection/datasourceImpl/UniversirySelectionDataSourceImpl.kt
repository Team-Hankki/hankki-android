package com.hankki.data.universityselection.datasourceImpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.universityselection.datasource.UniversitySelectionDataSource
import com.hankki.data.universityselection.dto.request.UniversitySelectionRequestDto
import com.hankki.data.universityselection.dto.response.University
import com.hankki.data.universityselection.dto.response.UniversitySelectionResponseDto
import com.hankki.data.universityselection.service.UniversitySelectionService
import javax.inject.Inject

class UniversirySelectionDataSourceImpl @Inject constructor(
    private val universitySelectionService: UniversitySelectionService
) : UniversitySelectionDataSource {
    override suspend fun getUniversitySelection(): BaseResponse<UniversitySelectionResponseDto> =
        universitySelectionService.getUniversitySelection()

    override suspend fun postUniversitySelection(request: UniversitySelectionRequestDto): CreatedBaseResponse =
        universitySelectionService.postUniversitySelection(request)
}
