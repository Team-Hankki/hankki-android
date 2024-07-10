package com.hankki.feature.my.myjogbo

import androidx.lifecycle.ViewModel
import com.hankki.domain.my.entity.MyJogboEntity
import com.hankki.feature.my.myjogbo.model.toMyJogboModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyJogboViewModel @Inject constructor(
) : ViewModel() {
    private val _myJogboState = MutableStateFlow(MyJogboState())
    val myJogboState: StateFlow<MyJogboState>
        get() = _myJogboState.asStateFlow()

    fun getMockJogboList() {
        _myJogboState.value = _myJogboState.value.copy(
            myJogboItems = persistentListOf(
                MyJogboEntity(
                    1,
                    jogboName = "족보이름테스트세트스테스테스트테스트테스",
                ).toMyJogboModel(),
                MyJogboEntity(
                    2,
                    jogboName = "족보이름2",
                ).toMyJogboModel(),
                MyJogboEntity(
                    3,
                    jogboName = "족보이름3",
                ).toMyJogboModel()
            )
        )
    }
}
