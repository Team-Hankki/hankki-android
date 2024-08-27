package com.hankki.feature.universityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequestEntity
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitySelectionViewModel @Inject constructor(
    private val universitySelectionRepository: UniversitySelectionRepository
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
            universitySelectionRepository.getUniversitySelection().onSuccess { universities ->
                _universitySelectionState.value = _universitySelectionState.value.copy(
                    universities = universities.toPersistentList(),
                    selectedUniversity = null
                )
            }.onFailure {
                // Handle error
            }
        }
    }

    fun enterUniversitySelection() {
        _universitySelectionState.value = _universitySelectionState.value.copy(
            selectedUniversity = null
        )
    }


    fun selectUniversity(university: UniversitySelectionEntity) {
        _universitySelectionState.value =
            _universitySelectionState.value.copy(selectedUniversity = university)
    }

    fun postUniversity() {
        _universitySelectionState.value.selectedUniversity?.let { selectedUniversity ->
            viewModelScope.launch {
                universitySelectionRepository.postUniversitySelection(
                    UniversitySelectionRequestEntity(
                        universityId = selectedUniversity.id.toLong(),
                        name = selectedUniversity.name,
                        longitude = selectedUniversity.longitude,
                        latitude = selectedUniversity.latitude
                    )
                ).onSuccess {
                    _sideEffects.emit(UniversitySelectionSideEffect.PostSuccess)
                }.onFailure {
                    _sideEffects.emit(UniversitySelectionSideEffect.PostError(it))
                }
            }
        }
    }
}
