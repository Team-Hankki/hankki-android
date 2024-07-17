package com.hankki.feature.universityselection

import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class UniversitySelectionState(
    val universities: PersistentList<UniversitySelectionEntity> = persistentListOf(),
    val selectedUniversity: UniversitySelectionEntity? = null
)
