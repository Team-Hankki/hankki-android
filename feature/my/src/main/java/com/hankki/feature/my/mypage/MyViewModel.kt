package com.hankki.feature.my.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.my.repository.MyRepository
import com.hankki.domain.token.repository.TokenRepository
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
    private val myRepository: MyRepository,
    private val tokenRepository: TokenRepository
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
                        myModel = information.toModel(),
                        uiState = UiState.Success(information.toModel())
                    )
                }.onFailure {
                    _myState.value = _myState.value.copy(uiState = UiState.Failure)
                    Timber.e(it)
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
            dialogSate = state
        )
    }

    fun logout() {
        viewModelScope.launch {
            _myState.value = _myState.value.copy(uiState = UiState.Loading)
            runCatching {
                myRepository.patchLogout()
            }.onSuccess {
                tokenRepository.clearInfo()
                _mySideEffect.emit(MySideEffect.ShowLogoutSuccess)
            }.onFailure {
                _myState.value = _myState.value.copy(uiState = UiState.Failure)
                Timber.e(it)
            }
        }
    }

    fun deleteWithdraw() {
        viewModelScope.launch {
            _myState.value = _myState.value.copy(uiState = UiState.Loading)
            runCatching {
                myRepository.deleteWithdraw()
            }.onSuccess {
                tokenRepository.clearInfo()
                _mySideEffect.emit(MySideEffect.ShowDeleteWithdrawSuccess)
            }.onFailure {
                _myState.value = _myState.value.copy(uiState = UiState.Failure)
                Timber.e(it)
            }
        }
    }

    companion object {
        const val LIKE = "like"
        const val REPORT = "report"
        const val TERMS_OF_USE = "terms_of_use"
        const val INQUIRY = "inquiry"
        const val TERMS_OF_USE_PAGE =
            "https://fast-kilometer-dbf.notion.site/FAQ-bb4d74b681d14f4f91bbbcc829f6d023"
        const val INQUIRY_PAGE = "https://tally.so/r/mO0oJY"
    }
}
