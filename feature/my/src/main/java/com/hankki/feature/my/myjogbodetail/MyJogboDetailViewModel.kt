package com.hankki.feature.my.myjogbodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.mystore.MyStoreSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
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
class MyJogboDetailViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _myJogboDetailState = MutableStateFlow(MyJogboDetailState())
    val myJogboDetailState: StateFlow<MyJogboDetailState>
        get() = _myJogboDetailState.asStateFlow()

    private val _mySideEffect: MutableSharedFlow<MyJogboSideEffect> = MutableSharedFlow()
    val mySideEffect: SharedFlow<MyJogboSideEffect>
        get() = _mySideEffect.asSharedFlow()

    fun getJogboDetail(favoriteId : Long) {
        viewModelScope.launch {
            myRepository.getJogboDetail(favoriteId)
                .onSuccess { jogbo ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        myStoreItems = jogbo
                    )
                }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateDeleteDialog(state: Boolean) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            showDeleteDialog = !state
        )
    }

    fun updateShareDialog(state: Boolean) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            showShareDialog = !state
        )
    }

    fun deleteJogboStore(favoriteId:Long,storeId:Long){
        viewModelScope.launch {
            myRepository.deleteJogboStore(favoriteId,storeId)
                .onSuccess { jogbo ->
                    updateDeleteDialog(true)
                    getJogboDetail(favoriteId)
                }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateSelectedStoreId(storeId : Long){
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            selectedStoreId = storeId
        )
    }

    fun onClickStoreItem(storeId : Long){
        viewModelScope.launch {

        }
    }
}
