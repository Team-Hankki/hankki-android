package com.hankki.core.common.utill

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data object Failure : UiState<Nothing>
}

sealed interface EmptyUiState<out T> {
    data object Loading : EmptyUiState<Nothing>

    data object Empty : EmptyUiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : EmptyUiState<T>

    data object Failure : EmptyUiState<Nothing>
}
