package com.hankki.domain.universityselection.repository

import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequestEntity

interface UniversitySelectionRepository {
    suspend fun getUniversitySelection():Result<List<UniversitySelectionEntity>>
    suspend fun postUniversitySelection(request: UniversitySelectionRequestEntity): Result<Unit>
}
