package com.hankki.feature.my.newjogbo

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
        updateButtonState(_newJogboState.value.title, tags)
    }

    fun updateButtonState(title: String, tags: String) {
        val tagsList = tags.split("#").filter { it.isNotBlank() }.joinToString("#", prefix = "#")

        _newJogboState.value = _newJogboState.value.copy(
            isButtonEnabled = title.isNotEmpty() && tagsList.isNotEmpty()
        )
    }

    fun editTagsLength(tags: String): Int {
        val tagsWithoutHash = tags.replace("#", "")
        return tagsWithoutHash.length
    }
}
