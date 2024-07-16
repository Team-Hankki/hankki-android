package com.hankki.domain.universityselection.repository

import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequest

interface UniversitySelectionRepository {
    suspend fun getUniversitySelection():Result<List<UniversitySelectionEntity>>
    suspend fun postUniversitySelection(request: UniversitySelectionRequest): Result<UniversitySelectionEntity>
}
