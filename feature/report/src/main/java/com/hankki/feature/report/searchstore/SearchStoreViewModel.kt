package com.hankki.feature.report.searchstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.report.repository.ReportRepository
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchStoreViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<SearchStoreState> = MutableStateFlow(SearchStoreState())
    val state: StateFlow<SearchStoreState>
        get() = _state.asStateFlow()

    fun setValue(value: String) {
        _state.value = _state.value.copy(value = value)
        getStores(value)
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(selectedLocation = location)
    }

    fun getStores(search: String) { // duration을 통해 호출할 예정
        viewModelScope.launch {
            reportRepository.getStoreLocation(search)
                .onSuccess {
                    _state.value = _state.value.copy(
                        uiState = EmptyUiState.Success(
                            data = it.map { it.toModel() }.toPersistentList()
                        )
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }
}
