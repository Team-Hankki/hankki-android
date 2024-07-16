package com.hankki.feature.report.searchstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity
import com.hankki.domain.report.repository.ReportRepository
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchStoreViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel() {
    private val _value: MutableStateFlow<String> = MutableStateFlow("")
    val value: StateFlow<String>
        get() = _value.asStateFlow()

    private val _sideEffect: MutableSharedFlow<SearchStoreSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<SearchStoreSideEffect>
        get() = _sideEffect.asSharedFlow()

    private val _state: MutableStateFlow<SearchStoreState> = MutableStateFlow(SearchStoreState())
    val state: StateFlow<SearchStoreState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _value.debounce(500)
                .collectLatest { debounced ->
                    if (debounced.isNotBlank()) {
                        getStores(debounced)
                    }
                }
        }
    }

    fun setValue(value: String) {
        _value.value = value
        if (value.isBlank()) {
            _state.value = _state.value.copy(uiState = EmptyUiState.Loading)
        }
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(selectedLocation = location)
    }

    private fun getStores(search: String) {
        viewModelScope.launch {
            reportRepository.getStoreLocation(search)
                .onSuccess {
                    _state.value = _state.value.copy(
                        uiState = if (it.isEmpty()) {
                            EmptyUiState.Empty
                        } else {
                            EmptyUiState.Success(
                                data = it.map { it.toModel() }.toPersistentList()
                            )
                        }
                    )
                }.onFailure { error ->
                    Timber.e(error)
                    _state.value = _state.value.copy(
                        uiState = EmptyUiState.Failure
                    )
                }
        }
    }

    fun reportButtonClicked() {
        viewModelScope.launch {
            reportRepository.getStoreValidate(
                ValidateStoreRequestEntity(
                    universityId = _state.value.universityId,
                    latitude = _state.value.selectedLocation.latitude.toDouble(),
                    longitude = _state.value.selectedLocation.longitude.toDouble()
                )
            ).onSuccess {
                with(_state.value.selectedLocation) {
                    _sideEffect.emit(
                        SearchStoreSideEffect.ValidateUniversitySuccess(
                            latitude = latitude,
                            longitude = longitude,
                            location = location,
                            address = address
                        )
                    )
                }
            }.onFailure { error ->
                Timber.e(error) // TODO: SnackBar 머지시 409 -> openDialog, else Snackbar 설정
                _sideEffect.emit(SearchStoreSideEffect.OpenDialog)
            }
        }
    }
}
