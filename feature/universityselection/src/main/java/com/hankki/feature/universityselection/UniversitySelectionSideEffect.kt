package com.hankki.feature.universityselection

sealed class UniversitySelectionSideEffect {
    data class PostSuccess(val universityName: String?) : UniversitySelectionSideEffect()
    data class PostError(val error: Throwable) : UniversitySelectionSideEffect()
}
