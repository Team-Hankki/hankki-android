package com.hankki.feature.my.mystore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.mystore.model.toMyStoreModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun getLikedStoreList() {
        viewModelScope.launch {
            myRepository.getLikedStore()
                .onSuccess { storeList ->
                    _myStoreState.value = _myStoreState.value.copy(
                        myStoreItems = storeList.map {
                            it.toMyStoreModel()
                        }.toPersistentList()
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
                        myStoreItems = storeList.map {
                            it.toMyStoreModel()
                        }.toPersistentList()
                    )
                }
                .onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun updateStoreSelected(index: Int, isStoreSelected: Boolean) {
        if (_myStoreState.value.myStoreItems.size <= index) return
        if (_myStoreState.value.myStoreItems.isEmpty()) return

        _myStoreState.value = _myStoreState.value.copy(
            myStoreItems = _myStoreState.value.myStoreItems.set(
                index,
                _myStoreState.value.myStoreItems[index].copy(isLiked = !isStoreSelected)
            )
        )
    }
}
