package com.hankki.feature.dummy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.dummy.repository.ReqresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val requresRepository: ReqresRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<DummyState> = MutableStateFlow(DummyState())
    val state: StateFlow<DummyState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<DummySideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<DummySideEffect>
        get() = _sideEffect.asSharedFlow()

    fun getUserList() {
        viewModelScope.launch {
            requresRepository.getUserList(1)
                .onSuccess {
                    _state.value = _state.value.copy(users = UiState.Success(it.toPersistentList()))
                }.onFailure {
                    _state.value = _state.value.copy(users = UiState.Failure)
                    _sideEffect.emit(DummySideEffect.SnackBar("우앵 에러났서요"))
                }
        }
    }
}
