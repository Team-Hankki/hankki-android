package com.hankki.feature.my.myjogbo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.myjogbo.model.toMyJogboModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateMode() {
        _myJogboState.value = _myJogboState.value.copy(
            editMode = !_myJogboState.value.editMode
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

    fun resetJogboState() {
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
            updateMode()
        }
    }

    fun updateToDialogState(state: Boolean) {
        _myJogboState.value = _myJogboState.value.copy(
            showDialog = state
        )
    }

    fun deleteJogboStore() {
        val currentState = _myJogboState.value.uiState
        if (currentState is UiState.Success) {
            val jogboItems = currentState.data
            val deleteList = jogboItems.filter {
                it.jogboSelected
            }.map {
                it.jogboId
            }
            viewModelScope.launch {
                myRepository.deleteJogboStores(deleteList)
                    .onSuccess {
                        _myJogboState.value = _myJogboState.value.copy(
                            showDialog = false,
                            editMode = false
                        )
                        getMyJogboList()
                    }
                    .onFailure { error ->
                        Timber.e(error)
                    }
            }
        }
    }
}
