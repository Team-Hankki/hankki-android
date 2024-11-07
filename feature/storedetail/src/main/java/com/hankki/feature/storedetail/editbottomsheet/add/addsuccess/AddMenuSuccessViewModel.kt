package com.hankki.feature.storedetail.editbottomsheet.add.addsuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMenuSuccessViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddMenuSuccessState())
    val state = _state.asStateFlow()

    init {
        fetchNickname()
    }

    fun setSubmittedMenuCount(count: Int) {
        _state.update { it.copy(submittedMenuCount = count) }
    }

    private fun fetchNickname() {
        viewModelScope.launch {
            storeDetailRepository.getStoreDetailNickname()
                .onSuccess { response ->
                    _state.update { it.copy(nickname = response.nickname) }
                }
                .onFailure {
                    // Error handling
                }
        }
    }
}