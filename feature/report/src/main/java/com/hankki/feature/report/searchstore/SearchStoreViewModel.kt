package com.hankki.feature.report.searchstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity
import com.hankki.domain.report.repository.ReportRepository
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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
        getUniversityId()
        viewModelScope.launch {
            _value.debounce(DEBOUNCE_DURATION)
                .collectLatest { debounced ->
                    _state.value = _state.value.copy(
                        selectedLocation = LocationModel()
                    )
                    if (debounced.isNotBlank()) {
                        getStores(debounced)
                    }
                }
        }
    }

    private fun getUniversityId() {
        viewModelScope.launch {
            reportRepository.getMyUniversity()
                .onSuccess {
                    _state.value = _state.value.copy(
                        universityId = it.id
                    )
                }
        }
    }

    fun setValue(value: String) {
        _value.value = value
        if (value.isBlank()) {
            _state.value = _state.value.copy(uiState = EmptyUiState.Success(persistentListOf()))
        }
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(selectedLocation = location)
    }

    fun setDialogState(isOpen: Boolean) {
        _state.value = _state.value.copy(isOpenDialog = isOpen)
    }

    private fun getStores(search: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(uiState = EmptyUiState.Loading)
            reportRepository.getStoreLocation(search)
                .onSuccess { stores ->
                    _state.value = _state.value.copy(
                        uiState = if (stores.isEmpty()) {
                            EmptyUiState.Empty
                        } else {
                            EmptyUiState.Success(
                                data = stores.map { store ->
                                    store.toModel()
                                }.toPersistentList()
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
                    longitude = _state.value.selectedLocation.longitude.toDouble(),
                    storeName = _state.value.selectedLocation.location
                )
            ).onSuccess { response ->
                when {
                    response.id == null -> {
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
                    }

                    response.isRegistered -> {
                        _state.value = _state.value.copy(
                            dialogState = DialogState.MySchool(response.id ?: 0),
                            isOpenDialog = true
                        )
                    }

                    else -> {
                        _state.value = _state.value.copy(
                            dialogState = DialogState.OtherSchool(response.id ?: 0),
                            isOpenDialog = true
                        )
                    }
                }
            }.onFailure(Timber::e)
        }
    }

    fun postUniversityStore(storeId: Long) {
        viewModelScope.launch {
            reportRepository.postUniversityStore(
                storeId = storeId,
                universityId = _state.value.universityId
            ).onSuccess {
                navigateToStoreDetail(storeId)
            }.onFailure(Timber::e)
        }
    }

    fun navigateToStoreDetail(storeId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(SearchStoreSideEffect.NavigateToStoreDetail(storeId))
        }
    }

    companion object {
        private const val DEBOUNCE_DURATION: Long = 300
    }
}
