package com.hankki.feature.report.finish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportFinishViewModel @Inject constructor(
) : ViewModel() {
    private val _state: MutableStateFlow<ReportFinishState> = MutableStateFlow(ReportFinishState())
    val state: StateFlow<ReportFinishState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<ReportFinishSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<ReportFinishSideEffect>
        get() = _sideEffect.asSharedFlow()


    init {
        getUserName()
    }

    fun setStoreInfo(
        count: Long,
        storeName: String,
        storeId: Long,
    ) {
        _state.value = _state.value.copy(
            count = count,
            storeName = storeName,
            storeId = storeId
        )
    }

    fun getUserName() {
        _state.value = _state.value.copy(name = "동민")
    }

    fun addMyJogbo() {
        // TODO: api 연결
    }

    fun navigateToStoreDetail() {
        viewModelScope.launch {
            _sideEffect.emit(ReportFinishSideEffect.navigateToStoreDetail(_state.value.storeId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _sideEffect.emit(ReportFinishSideEffect.navigateToHome)
        }
    }
}
