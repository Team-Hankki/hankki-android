package com.hankki.feature.my.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.entity.MyRepository
import com.hankki.feature.my.mypage.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _myState = MutableStateFlow(MyState())
    val myState: StateFlow<MyState>
        get() = _myState.asStateFlow()

    fun getUserInformation() {
        viewModelScope.launch {
            myRepository.getUserInformation()
                .onSuccess { information ->
                    _myState.value = _myState.value.copy(
                        myModel = information.toModel()
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    companion object {
        const val LIKE = "like"
        const val REPORT = "report"
        const val FAQ = "FAQ"
        const val INQUIRY = "inquiry"
        const val LOGOUT = "logout"
        const val QUIT = "quit"
    }
}
