package com.hankki.feature.my.myjogbo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.entity.MyRepository
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
    private val myRepository: MyRepository
) : ViewModel() {
    private val _myJogboState = MutableStateFlow(MyJogboState())
    val myJogboState: StateFlow<MyJogboState>
        get() = _myJogboState.asStateFlow()

    fun getMyJogboList() {
        viewModelScope.launch {
            myRepository.getMyJogboList().onSuccess { jogboList ->
                _myJogboState.value = _myJogboState.value.copy(
                    myJogboItems = jogboList.map {
                        it.toMyJogboModel()
                    }.toPersistentList()
                )
            }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateMode() {
        _myJogboState.value = _myJogboState.value.copy(
            editMode = mutableStateOf(
                !_myJogboState.value.editMode.value
            )
        )
    }

    fun updateJogboSeleted(index: Int, isJogboSelected: Boolean) {
        if (_myJogboState.value.myJogboItems.size <= index) return
        if (_myJogboState.value.myJogboItems.isEmpty()) return

        _myJogboState.value = _myJogboState.value.copy(
            myJogboItems = _myJogboState.value.myJogboItems.set(
                index,
                _myJogboState.value.myJogboItems[index].copy(jogboSelected = isJogboSelected)
            )
        )
    }

    fun resetJogboState() {
        _myJogboState.value =
            _myJogboState.value.copy(myJogboItems = _myJogboState.value.myJogboItems.map { jogboItem ->
                jogboItem.copy(jogboSelected = false)
            }.toPersistentList())
        updateMode()
    }

    fun updateToDialogState(state:Boolean){
        _myJogboState.value = _myJogboState.value.copy(
            showDialog = state
        )
    }
}
