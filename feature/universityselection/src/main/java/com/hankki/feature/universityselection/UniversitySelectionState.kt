package com.hankki.feature.universityselection

import com.hankki.core.common.utill.UiState
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import kotlinx.collections.immutable.PersistentList

data class UniversitySelectionState(
    val universities: UiState<PersistentList<UniversitySelectionEntity>> = UiState.Loading,
    val selectedUniversity: UniversitySelectionEntity? = null,
)
