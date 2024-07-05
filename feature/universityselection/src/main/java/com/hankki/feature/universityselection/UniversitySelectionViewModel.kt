package com.hankki.feature.universityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.universityselection.UniversitySelectionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UniversitySelectionViewModel @Inject constructor() : ViewModel() {
    private val _universities = MutableStateFlow<List<UniversitySelectionModel>>(emptyList())
    val universities: StateFlow<List<UniversitySelectionModel>> = _universities

    private val _selectedUniversity = MutableStateFlow<String?>(null)
    val selectedUniversity: StateFlow<String?> = _selectedUniversity

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            val dummyData = listOf(
                UniversitySelectionModel(1, "한양대"), UniversitySelectionModel(2, "성신여대"),
                UniversitySelectionModel(3, "성균관대"), UniversitySelectionModel(4, "건국대"),
                UniversitySelectionModel(5, "경희대"), UniversitySelectionModel(6, "외대"),
                UniversitySelectionModel(7, "연세대"), UniversitySelectionModel(8, "이화여대"),
                UniversitySelectionModel(9, "홍익대"), UniversitySelectionModel(10, "숭실대"),
                UniversitySelectionModel(11, "고려대"), UniversitySelectionModel(12, "중앙대"),
                UniversitySelectionModel(13, "동국대"), UniversitySelectionModel(14, "서강대"),
                UniversitySelectionModel(15, "경기대"), UniversitySelectionModel(16, "숙명여대"),
                UniversitySelectionModel(17, "단국대"), UniversitySelectionModel(18, "명지대"),
                UniversitySelectionModel(19, "서울대"), UniversitySelectionModel(20, "국민대")
            ).sortedBy { it.name }
            _universities.value = dummyData
        }
    }

    fun selectUniversity(university: String) {
        _selectedUniversity.value = university
    }
}