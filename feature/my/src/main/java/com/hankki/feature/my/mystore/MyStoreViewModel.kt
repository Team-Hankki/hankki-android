package com.hankki.feature.my.mystore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.mystore.model.toMyStoreModel
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
class MyStoreViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _myStoreState = MutableStateFlow(MyStoreState())
    val myStoreState: StateFlow<MyStoreState>
        get() = _myStoreState.asStateFlow()

    private val _mySideEffect: MutableSharedFlow<MyStoreSideEffect> = MutableSharedFlow()
    val mySideEffect: SharedFlow<MyStoreSideEffect>
        get() = _mySideEffect.asSharedFlow()

    fun getLikedStoreList() {
        viewModelScope.launch {
            myRepository.getLikedStore()
                .onSuccess { storeList ->
                    _myStoreState.value = _myStoreState.value.copy(
                        uiState = if(storeList.isEmpty()){
                            EmptyUiState.Empty
                        } else{
                            EmptyUiState.Success(
                                storeList.map {
                                    it.toMyStoreModel()
                                }.toPersistentList()
                            )
                        }
                    )
                }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun getReportedStoreList() {
        viewModelScope.launch {
            myRepository.getReportedStore()
                .onSuccess { storeList ->
                    _myStoreState.value = _myStoreState.value.copy(
                        uiState = if(storeList.isEmpty()){
                            EmptyUiState.Empty
                        }else {
                            EmptyUiState.Success(
                                storeList.map {
                                    it.toMyStoreModel()
                                }.toPersistentList()
                            )
                        }
                    )
                }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateStoreSelected(id: Long, isLiked: Boolean) {
        viewModelScope.launch {
            if (isLiked) {
                myRepository.unLikeStore(id)
                    .onSuccess {
                        val currentState = _myStoreState.value.uiState
                        if (currentState is EmptyUiState.Success) {
                            val updatedItems = currentState.data.map {
                                if (it.id == id) {
                                    it.copy(isLiked = false)
                                } else {
                                    it
                                }
                            }.toPersistentList()
                            _myStoreState.value = _myStoreState.value.copy(
                                uiState = EmptyUiState.Success(updatedItems)
                            )
                        }
                    }
                    .onFailure { error ->
                        Timber.e(error)
                    }
            } else {
                myRepository.likeStore(id)
                    .onSuccess {
                        val currentState = _myStoreState.value.uiState
                        if (currentState is EmptyUiState.Success) {
                            val updatedItems = currentState.data.map {
                                if (it.id == id) {
                                    it.copy(isLiked = true)
                                } else {
                                    it
                                }
                            }.toPersistentList()
                            _myStoreState.value = _myStoreState.value.copy(
                                uiState = EmptyUiState.Success(updatedItems)
                            )
                        }
                    }
                    .onFailure { error ->
                        Timber.e(error)
                    }
            }
        }
    }


//    fun updateStoreSelected(id: Long, isLiked: Boolean) {
//        viewModelScope.launch {
//            if (isLiked) {
//                myRepository.unLikeStore(id).onSuccess {
//                    _myStoreState.value = _myStoreState.value.copy(
//                        uiState = _myStoreState.value.uiState.map {
//                            if (it.id == id) {
//                                it.copy(isLiked = false)
//                            } else {
//                                it
//                            }
//                        }.toPersistentList()
//                    )
//                }
//            } else {
//                myRepository.likeStore(id).onSuccess {
//                    _myStoreState.value = _myStoreState.value.copy(
//                        uiState = _myStoreState.value.uiState.map {
//                            if (it.id == id) {
//                                it.copy(isLiked = true)
//                            } else {
//                                it
//                            }
//                        }.toPersistentList()
//                    )
//                }
//            }
//        }
//    }

    fun onClickItem(id: Long) {
        viewModelScope.launch {
            _mySideEffect.emit(MyStoreSideEffect.NavigateToDetail(id))
        }
    }
}
