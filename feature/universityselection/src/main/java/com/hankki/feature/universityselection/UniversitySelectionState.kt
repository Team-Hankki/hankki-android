package com.hankki.feature.universityselection

import com.hankki.domain.universityselection.UniversitySelectionModel

data class UniversitySelectionState(
    val universities: List<UniversitySelectionModel> = emptyList(),
    val selectedUniversity: String? = null
)