package com.hankki.feature.my.myjogbodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.my.entity.response.UserInformationEntity
import com.hankki.domain.my.repository.MyRepository
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
class MyJogboDetailViewModel @Inject constructor(
    private val myRepository: MyRepository,
) : ViewModel() {
    private val _myJogboDetailState = MutableStateFlow(MyJogboDetailState())
    val myJogboDetailState: StateFlow<MyJogboDetailState>
        get() = _myJogboDetailState.asStateFlow()

    private val _mySideEffect: MutableSharedFlow<MyJogboDetailSideEffect> = MutableSharedFlow()
    val mySideEffect: SharedFlow<MyJogboDetailSideEffect>
        get() = _mySideEffect.asSharedFlow()

    fun getJogboDetail(favoriteId: Long) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            uiState = EmptyUiState.Loading
        )
        viewModelScope.launch {
            myRepository.getJogboDetail(favoriteId)
                .onSuccess { jogbo ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        myStoreItems = jogbo,
                        uiState = if (jogbo.stores.isEmpty()) EmptyUiState.Empty else EmptyUiState.Success(jogbo.stores.toPersistentList())
                    )
                }
                .onFailure(Timber::e)
        }
    }

    fun updateDeleteDialogState(state: Boolean) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            deleteDialogState = !state
        )
    }

    fun updateShareDialogState(state: Boolean) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            shareDialogState = !state
        )
    }

    fun deleteSelectedStore(favoriteId:Long, storeId:Long){
        viewModelScope.launch {
            myRepository.deleteJogboStore(favoriteId,storeId)
                .onSuccess { jogbo ->
                    updateDeleteDialogState(true)
                    getJogboDetail(favoriteId)
                }
                .onFailure(Timber::e)
        }
    }

    fun updateSelectedStoreId(storeId: Long) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            selectedStoreId = storeId
        )
    }

    fun navigateToStoreDetail(storeId: Long) {
        viewModelScope.launch {
            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToDetail(storeId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToHome)
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            myRepository.getUserInformation()
                .onSuccess { userInformation ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        userInformation = UserInformationEntity(
                            nickname = userInformation.nickname
                        )
                    )
                }
                .onFailure(Timber::e)
        }
    }
}
