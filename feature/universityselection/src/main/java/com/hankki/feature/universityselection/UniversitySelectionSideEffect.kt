package com.hankki.feature.universityselection

sealed class UniversitySelectionSideEffect {
    data object PostSuccess : UniversitySelectionSideEffect()
    data class PostError(val error: Throwable) : UniversitySelectionSideEffect()
}
