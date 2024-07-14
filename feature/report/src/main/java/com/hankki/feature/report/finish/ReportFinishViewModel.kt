package com.hankki.feature.report.finish

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReportFinishViewModel @Inject constructor(
) : ViewModel() {
    private val _state: MutableStateFlow<ReportFinishState> = MutableStateFlow(ReportFinishState())
    val state: StateFlow<ReportFinishState>
        get() = _state.asStateFlow()

    init {
        getUserName()
    }

    fun setStoreInfo() {
        _state.value = _state.value.copy(
            count = 1,
            storeName = "고동밥집",
            storeId = 1
        )
    }

    fun getUserName() {
        _state.value = _state.value.copy(name = "동민")
    }

    fun addMyJogbo() {
        // TODO: api 연결
    }

    fun moveToStoreDetail() {
        // TODO: SideEffect 발행
    }

    fun moveToHome() {
        // TODO: SideEffect 발행
    }
}
