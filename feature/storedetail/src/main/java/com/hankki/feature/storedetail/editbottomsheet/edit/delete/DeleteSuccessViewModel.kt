package com.hankki.feature.storedetail.editbottomsheet.edit.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteSuccessViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeleteSuccessState())
    val uiState: StateFlow<DeleteSuccessState> = _uiState

    private val _sideEffect = Channel<DeleteSuccessSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchNickname()
    }


    private fun fetchNickname() {
        viewModelScope.launch {
            storeDetailRepository.getStoreDetailNickname()
                .onSuccess { nickname ->
                    _uiState.value = DeleteSuccessState(nickname = nickname.nickname)
                }
                .onFailure {
                    // 처리
                }
        }
    }

    fun navigateToEditMenu() {
        viewModelScope.launch {
            _sideEffect.send(DeleteSuccessSideEffect.NavigateToEditMenu)
        }
    }

    fun navigateToStoreDetail() {
        viewModelScope.launch {
            _sideEffect.send(DeleteSuccessSideEffect.NavigateToStoreDetail)
        }
    }
}
