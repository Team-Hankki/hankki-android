package com.hankki.feature.universityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequestEntity
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitySelectionViewModel @Inject constructor(
    private val universitySelectionRepository: UniversitySelectionRepository,
) : ViewModel() {
    private val _universitySelectionState = MutableStateFlow(UniversitySelectionState())
    val universitySelectionState: StateFlow<UniversitySelectionState> = _universitySelectionState

    private val _sideEffects = MutableSharedFlow<UniversitySelectionSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        loadUniversities()
    }

    private fun loadUniversities() {
        viewModelScope.launch {
            _universitySelectionState.value =
                _universitySelectionState.value.copy(universities = UiState.Loading)
            universitySelectionRepository.getUniversitySelection().onSuccess { universities ->
                if (universities.isNotEmpty()) {
                    _universitySelectionState.value = _universitySelectionState.value.copy(
                        universities = UiState.Success(universities.toPersistentList())
                    )
                } else {
                    _universitySelectionState.value = _universitySelectionState.value.copy(
                        universities = UiState.Failure
                    )
                }
            }.onFailure {
                _universitySelectionState.value = _universitySelectionState.value.copy(
                    universities = UiState.Failure
                )
            }
        }
    }

    fun selectUniversity(university: UniversitySelectionEntity) {
        _universitySelectionState.value =
            _universitySelectionState.value.copy(selectedUniversity = university)
    }

    fun postUniversity() {
        val selectedUniversity = _universitySelectionState.value.selectedUniversity
        viewModelScope.launch {
            universitySelectionRepository.postUniversitySelection(
                if (selectedUniversity == null) {
                    with(DEFAULT_UNIVERSITY) {
                        UniversitySelectionRequestEntity(
                            universityId = id?.toLong(),
                            name = name,
                            longitude = longitude,
                            latitude = latitude
                        )
                    }
                } else {
                    with(selectedUniversity) {
                        UniversitySelectionRequestEntity(
                            universityId = id?.toLong(),
                            name = name,
                            longitude = longitude,
                            latitude = latitude
                        )
                    }
                }
            ).onSuccess {
                _sideEffects.emit(UniversitySelectionSideEffect.PostSuccess(selectedUniversity?.name))
            }.onFailure {
                _sideEffects.emit(UniversitySelectionSideEffect.PostError(it))
            }
        }

    }

    companion object {
        val DEFAULT_UNIVERSITY = UniversitySelectionEntity(
            id = null,
            name = "전체",
            latitude = 37.583639,
            longitude = 127.0588564
        )
    }
}
