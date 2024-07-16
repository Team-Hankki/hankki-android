package com.hankki.feature.my.mystore

import androidx.lifecycle.ViewModel
import com.hankki.domain.my.entity.StoreEntity
import com.hankki.feature.my.mystore.model.toMyStoreModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MyStoreViewModel @Inject constructor(
) : ViewModel() {
    private val _myStoreState = MutableStateFlow(MyStoreState())
    val myStoreState: StateFlow<MyStoreState>
        get() = _myStoreState.asStateFlow()

    fun getMockStoreList() {
        _myStoreState.value = _myStoreState.value.copy(
            myStoreItems = persistentListOf(
                StoreEntity(
                    "한식1",
                    100,
                    1,
                    "",
                    true,
                    0,
                    "가성비 맛집"
                ).toMyStoreModel(),
                StoreEntity(
                    "한식",
                    1,
                    1,
                    "",
                    true,
                    0,
                    "가성비 맛집",
                ).toMyStoreModel(),
                StoreEntity(
                    "한식3",
                    1,
                    1,
                    "",
                    true,
                    0,
                    "가성비 맛집",
                ).toMyStoreModel(),
                StoreEntity(
                    "한식4",
                    1,
                    1,
                    "",
                    true,
                    0,
                    "가성비 맛집",
                ).toMyStoreModel()
            )
        )
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
