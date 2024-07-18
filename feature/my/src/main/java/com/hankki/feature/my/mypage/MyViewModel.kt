package com.hankki.feature.my.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.repository.MyRepository
import com.hankki.feature.my.mypage.model.MySideEffect
import com.hankki.feature.my.mypage.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _myState = MutableStateFlow(MyState())
    val myState: StateFlow<MyState>
        get() = _myState.asStateFlow()

    private val _mySideEffect: MutableSharedFlow<MySideEffect> = MutableSharedFlow()
    val mySideEffect: SharedFlow<MySideEffect>
        get() = _mySideEffect.asSharedFlow()

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

    fun showWebView(type: String) {
        viewModelScope.launch {
            _mySideEffect.emit(MySideEffect.ShowWebView(type))
        }
    }

    fun updateDialogState(state: DialogState) {
        _myState.value = _myState.value.copy(
            showDialog = state
        )
    }

    companion object {
        const val LIKE = "like"
        const val REPORT = "report"
        const val FAQ = "FAQ"
        const val INQUIRY = "inquiry"
        const val FAQ_PAGE =
            "https://fast-kilometer-dbf.notion.site/FAQ-bb4d74b681d14f4f91bbbcc829f6d023?pvs=4"
        const val INQUIRY_PAGE = "https://tally.so/r/mO0oJY"
        const val NONE = ""
    }
}
