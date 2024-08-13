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
            storesUiState = EmptyUiState.Loading
        )
        viewModelScope.launch {
            myRepository.getJogboDetail(favoriteId)
                .onSuccess { jogbo ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        myStoreItems = jogbo,
                        storesUiState = if (jogbo.stores.isEmpty()) EmptyUiState.Empty else EmptyUiState.Success(jogbo.stores.toPersistentList())
                    )
                }
                .onFailure(Timber::e)
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
                            nickname = userInformation.nickname,
                            profileImageUrl = ""
                        )
                    )
                }
                .onFailure(Timber::e)
        }
    }
}
