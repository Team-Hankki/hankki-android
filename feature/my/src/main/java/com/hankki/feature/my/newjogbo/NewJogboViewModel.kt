package com.hankki.feature.my.newjogbo

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NewJogboViewModel @Inject constructor(
) : ViewModel() {
    private val _newJogboState = MutableStateFlow(NewJogboState())
    val newJogboState: StateFlow<NewJogboState>
        get() = _newJogboState.asStateFlow()

    fun setTitle(title: String) {
        _newJogboState.value = _newJogboState.value.copy(title = title)
        updateButtonState(title, _newJogboState.value.tags)
    }

    fun setTags(tags: String) {
        _newJogboState.value = _newJogboState.value.copy(tags = tags)
        Log.e("item","${_newJogboState.value.tags}")
        updateButtonState(_newJogboState.value.title, tags)
    }

    fun updateButtonState(title: String, tags: String) {
        _newJogboState.value = _newJogboState.value.copy(
            isButtonEnabled = title.isNotEmpty() && tags.isNotEmpty()
        )
    }
}
