package com.hankki.data.universityselection.repositoryImpl

import com.hankki.data.universityselection.datasource.UniversitySelectionDataSource
import com.hankki.data.universityselection.dto.request.toDto
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequestEntity
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
import javax.inject.Inject

class UniversitySelectionRepositoryImpl @Inject constructor(
    private val dataSource: UniversitySelectionDataSource
) : UniversitySelectionRepository {
    override suspend fun getUniversitySelection(): Result<List<UniversitySelectionEntity>> {
        return runCatching {
            dataSource.getUniversitySelection().data.universities.map { it.toUniversitySelectionEntity() }
        }
    }

    override suspend fun postUniversitySelection(request: UniversitySelectionRequestEntity): Result<Unit> {
        return runCatching {
            dataSource.postUniversitySelection(request.toDto())
        }
    }
}
