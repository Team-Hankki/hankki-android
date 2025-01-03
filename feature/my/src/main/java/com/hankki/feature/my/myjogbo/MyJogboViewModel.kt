package com.hankki.feature.my.myjogbo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.myjogbo.model.toMyJogboModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyJogboViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _myJogboState = MutableStateFlow(MyJogboState())
    val myJogboState: StateFlow<MyJogboState>
        get() = _myJogboState.asStateFlow()

    fun getMyJogboList() {
        viewModelScope.launch {
            myRepository.getMyJogboList()
                .onSuccess { jogboList ->
                    _myJogboState.value = _myJogboState.value.copy(
                        uiState = UiState.Success(
                            jogboList.map {
                                it.toMyJogboModel()
                            }.toPersistentList()
                        )
                    )
                }
                .onFailure(Timber::e)
        }
    }

    fun updateEditModeState() {
        _myJogboState.value = _myJogboState.value.copy(
            editModeState = !_myJogboState.value.editModeState
        )
    }

    fun updateJogboSeleted(index: Int, isJogboSelected: Boolean) {
        val currentState = _myJogboState.value.uiState
        if (currentState is UiState.Success) {
            val jogboItems = currentState.data
            if (jogboItems.size <= index) return
            if (jogboItems.isEmpty()) return

            _myJogboState.value = _myJogboState.value.copy(
                uiState = UiState.Success(
                    jogboItems.set(
                        index,
                        jogboItems[index].copy(jogboSelected = isJogboSelected)
                    )
                )
            )
        }
    }

    fun resetEditModeState() {
        val currentState = _myJogboState.value.uiState
        if (currentState is UiState.Success) {
            val jogboItems = currentState.data
            _myJogboState.value =
                _myJogboState.value.copy(
                    uiState = UiState.Success(
                        jogboItems.map { jogboItem ->
                            jogboItem.copy(jogboSelected = false)
                        }.toPersistentList()
                    )
                )
            updateEditModeState()
        }
    }

    fun updateDeleteDialogState() {
        _myJogboState.value = _myJogboState.value.copy(
            deleteDialogState = !_myJogboState.value.deleteDialogState,
            buttonEnabled = true
        )
    }

    fun deleteSelectedJogbo() {
        val currentState = _myJogboState.value.uiState
        if (currentState is UiState.Success) {
            val jogboItems = currentState.data
            val deleteList = jogboItems.filter { it.jogboSelected }.map { it.jogboId }
            viewModelScope.launch {
                _myJogboState.value = _myJogboState.value.copy(
                    buttonEnabled = false
                )
                myRepository.deleteJogboStores(deleteList)
                    .onSuccess {
                        _myJogboState.value = _myJogboState.value.copy(
                            deleteDialogState = false,
                            editModeState = false
                        )
                        getMyJogboList()
                    }
                    .onFailure(Timber::e)
            }
        }
    }

    fun updateNoExistsDialog() {
        _myJogboState.value = _myJogboState.value.copy(
            noExistsDialogState = !_myJogboState.value.noExistsDialogState,
        )
    }
}
