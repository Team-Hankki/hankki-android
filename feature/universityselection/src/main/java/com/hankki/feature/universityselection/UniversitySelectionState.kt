package com.hankki.feature.universityselection

import com.hankki.domain.universityselection.entity.UniversitySelectionEntity

data class UniversitySelectionState(
    val universities: List<UniversitySelectionEntity> = emptyList(),
    val selectedUniversity: UniversitySelectionEntity? = null
)
