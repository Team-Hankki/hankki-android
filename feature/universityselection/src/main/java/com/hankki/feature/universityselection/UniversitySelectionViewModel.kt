package com.hankki.feature.universityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.universityselection.entity.UniversitySelectionEntity
import com.hankki.domain.universityselection.entity.UniversitySelectionRequest
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniversitySelectionViewModel @Inject constructor(
    private val universitySelectionRepository: UniversitySelectionRepository
) : ViewModel() {

    private val _universitySelectionState = MutableStateFlow(UniversitySelectionState())
    val universitySelectionState: StateFlow<UniversitySelectionState> = _universitySelectionState

    init {
        loadUniversities()
    }

    private fun loadUniversities() {
        viewModelScope.launch {
            val result = universitySelectionRepository.getUniversitySelection()
            result.onSuccess { universities ->
                _universitySelectionState.value = _universitySelectionState.value.copy(
                    universities = universities
                )
            }.onFailure {
                // 에러 처리
            }
        }
    }

    fun selectUniversity(university: UniversitySelectionEntity) {
        _universitySelectionState.value =
            _universitySelectionState.value.copy(selectedUniversity = university)
    }

    fun postUniversity() {
        val selectedUniversity = _universitySelectionState.value.selectedUniversity
        if (selectedUniversity != null) {
            val request = UniversitySelectionRequest(
                universityId = selectedUniversity.id.toLong(),
                name = selectedUniversity.name,
                longitude = selectedUniversity.longitude,
                latitude = selectedUniversity.latitude
            )
            viewModelScope.launch {
                val result = universitySelectionRepository.postUniversitySelection(request)
                result.onSuccess { university ->
                    val updatedUniversities =
                        _universitySelectionState.value.universities + university

                    _universitySelectionState.value = _universitySelectionState.value.copy(
                        universities = updatedUniversities
                    )
                }.onFailure {
                    // 에러 처리
                }
            }
        }
    }
}
