package com.hankki.data.universityselection.repositoryImpl

import com.hankki.data.universityselection.datasource.UniversitySelectionDataSource
import com.hankki.data.universityselection.request.UniversitySelectionRequestDto
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequest
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
import javax.inject.Inject

class UniversitySelectionRepositoryImpl @Inject constructor(
    private val dataSource: UniversitySelectionDataSource
) : UniversitySelectionRepository {
    override suspend fun getUniversitySelection(): Result<List<UniversitySelectionEntity>> {
        return runCatching {
            val response = dataSource.getUniversitySelection()
            response.data.universities.map { it.toUniversitySelectionEntity() }
        }
    }

    override suspend fun postUniversitySelection(request: UniversitySelectionRequest): Result<UniversitySelectionEntity> {
        val dto = UniversitySelectionRequestDto(
            universityId = request.universityId,
            name = request.name,
            longitude = request.longitude,
            latitude = request.latitude
        )
        return runCatching {
            val response = dataSource.postUniversitySelection(dto)
            response.data.toUniversitySelectionEntity()
        }
    }
}
