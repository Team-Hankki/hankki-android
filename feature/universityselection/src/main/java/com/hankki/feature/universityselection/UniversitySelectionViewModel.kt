package com.hankki.feature.universityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.dummy.entity.response.UniversityModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UniversitySelectionViewModel @Inject constructor() : ViewModel() {
    private val _universities = MutableStateFlow<List<UniversityModel>>(emptyList())
    val universities: StateFlow<List<UniversityModel>> = _universities

    private val _selectedUniversity = MutableStateFlow<String?>(null)
    val selectedUniversity: StateFlow<String?> = _selectedUniversity

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            val dummyData = listOf(
                UniversityModel(1, "한양대"), UniversityModel(2, "성신여대"),
                UniversityModel(3, "성균관대"), UniversityModel(4, "건국대"),
                UniversityModel(5, "경희대"), UniversityModel(6, "외대"),
                UniversityModel(7, "연세대"), UniversityModel(8, "이화여대"),
                UniversityModel(9, "홍익대"), UniversityModel(10, "숭실대"),
                UniversityModel(11, "고려대"), UniversityModel(12, "중앙대"),
                UniversityModel(13, "동국대"), UniversityModel(14, "서강대"),
                UniversityModel(15, "경기대"), UniversityModel(16, "숙명여대"),
                UniversityModel(17, "단국대"), UniversityModel(18, "명지대"),
                UniversityModel(19, "서울대"), UniversityModel(20, "국민대")
            ).sortedBy { it.name }
            _universities.value = dummyData
        }
    }

    fun selectUniversity(university: String) {
        _selectedUniversity.value = university
    }
}