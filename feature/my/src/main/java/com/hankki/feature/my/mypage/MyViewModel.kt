package com.hankki.feature.my.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.feature.my.mypage.model.MyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor() : ViewModel() {
    private val _myState = MutableStateFlow(MyState())
    val myState: StateFlow<MyState>
        get() = _myState.asStateFlow()

    fun loadMockInformation() {
        viewModelScope.launch {
            val mockUserName = "송한끼"
            val mockUserImage = ""
            val mockUserInformation = MyModel(mockUserName, mockUserImage)
            _myState.value = _myState.value.copy(
                userState = mockUserInformation
            )
        }
    }
}
