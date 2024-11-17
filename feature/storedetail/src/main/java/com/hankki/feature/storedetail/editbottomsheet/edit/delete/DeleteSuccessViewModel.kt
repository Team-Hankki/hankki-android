package com.hankki.feature.storedetail.editbottomsheet.edit.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import com.hankki.feature.storedetail.editbottomsheet.add.addmenu.AddMenuSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeleteSuccessViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeleteSuccessState())
    val uiState: StateFlow<DeleteSuccessState> = _uiState

    private val _sideEffect = MutableSharedFlow<DeleteSuccessSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        fetchNickname()
    }

    private fun fetchNickname() {
        viewModelScope.launch {
            storeDetailRepository.getStoreDetailNickname()
                .onSuccess { nickname ->
                    _uiState.value = DeleteSuccessState(nickname = nickname.nickname)
                }
                .onFailure { error ->
                    Timber.e(error)
                    emitSideEffect(DeleteSuccessSideEffect.ShowError(error.message ?: "메뉴 삭제에 실패하였습니다. 다시 시도해 주세요."))
                }
        }
    }

    fun navigateToEditMenu() {
        viewModelScope.launch {
            _sideEffect.emit(DeleteSuccessSideEffect.NavigateToEditMenu)
        }
    }

    fun navigateToStoreDetail() {
        viewModelScope.launch {
            _sideEffect.emit(DeleteSuccessSideEffect.NavigateToStoreDetail)
        }
    }

    private suspend fun emitSideEffect(sideEffect: DeleteSuccessSideEffect) {
        _sideEffect.emit(sideEffect)
    }

}
