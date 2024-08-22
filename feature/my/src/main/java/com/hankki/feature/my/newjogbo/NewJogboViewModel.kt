package com.hankki.feature.my.newjogbo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.my.entity.request.NewJogboEntity
import com.hankki.domain.my.repository.MyRepository
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
import retrofit2.HttpException

@HiltViewModel
class NewJogboViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _newJogboState = MutableStateFlow(NewJogboState())
    val newJogboState: StateFlow<NewJogboState>
        get() = _newJogboState.asStateFlow()

    private val _newJogboSideEffect: MutableSharedFlow<NewJogboSideEffect> = MutableSharedFlow()
    val newJogboSideEffect: SharedFlow<NewJogboSideEffect>
        get() = _newJogboSideEffect.asSharedFlow()

    fun setTitle(title: String) {
        _newJogboState.value = _newJogboState.value.copy(title = title)
        updateButtonState(title, _newJogboState.value.tags)
    }

    fun setTags(tags: String) {
        _newJogboState.value = _newJogboState.value.copy(tags = tags)
        updateButtonState(_newJogboState.value.title, tags)
    }

    fun updateButtonState(title: String, tags: String) {
        val tagsList = tags.replace("#", "")

        _newJogboState.value = _newJogboState.value.copy(
            isButtonEnabled = title.isNotEmpty() && tagsList.isNotEmpty()
        )
    }

    fun editTagsLength(tags: String): Int {
        val tagsWithoutHash = tags.replace("#", "")
        return tagsWithoutHash.length
    }

    fun createNewJogbo() {
        viewModelScope.launch {
            myRepository.createNewJogbo(
                NewJogboEntity(
                    title = _newJogboState.value.title,
                    details = _newJogboState.value.tags.split("#").filter { it.isNotBlank() }
                        .map { "#$it" }
                )
            ).onSuccess {
                _newJogboSideEffect.emit(NewJogboSideEffect.NavigateToNewJogbo)
            }.onFailure { error ->
                Timber.e(error)

                if (error is HttpException && error.code() == DUPLICATE_NAME_ERROR) {
                    _newJogboSideEffect.emit(NewJogboSideEffect.ShowErrorDialog)
                }
            }
        }
    }

    fun updateErrorDialog(state: Boolean) {
        _newJogboState.value = _newJogboState.value.copy(
            errorDialogState = !state,
        )
    }

    fun resetTitle() {
        _newJogboState.value = _newJogboState.value.copy(
            title = ""
        )
    }

    companion object {
        private const val DUPLICATE_NAME_ERROR: Int = 409
    }
}
