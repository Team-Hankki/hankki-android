package com.hankki.feature.my.myjogbodetail

import androidx.lifecycle.ViewModel
import com.hankki.domain.my.entity.response.MyJogboDetailEntity
import com.hankki.domain.my.entity.response.Store
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MyJogboDetailViewModel @Inject constructor(
) : ViewModel() {
    private val _myJogboDetailState = MutableStateFlow(MyJogboDetailState())
    val myJogboDetailState: StateFlow<MyJogboDetailState>
        get() = _myJogboDetailState.asStateFlow()

    fun getMockStoreList() {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            myStoreItems = MyJogboDetailEntity(
                title = "",
                chips = listOf("", "").toPersistentList(),
                stores = persistentListOf(
                    Store(0, "?", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0)
                )
            )
        )
    }

    fun updateDeleteDialog(state :Boolean){
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            showDeleteDialog = !state
        )
    }

    fun updateShareDialog(state :Boolean){
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            showShareDialog = !state
        )
    }
}
