package com.hankki.feature.my

import androidx.lifecycle.ViewModel
import com.hankki.domain.my.entity.MyJogboDetailEntity
import com.hankki.domain.my.entity.Store
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
                tags = listOf("", ""),
                stores = listOf(
                    Store(0, "", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0),
                    Store(0, "", "", "", 0, 0)
                )
            )
        )
    }
}
