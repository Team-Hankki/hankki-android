package com.hankki.feature.storedetail.editbottomsheet.edit.editmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.entity.MenuItem
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMenuViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditMenuState())
    val uiState: StateFlow<EditMenuState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<EditMenuSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _dialogState = MutableStateFlow(EditMenuDialogState.CLOSED)
    val dialogState: StateFlow<EditMenuDialogState> = _dialogState

    private var selectedMenuId: Long = -1


    fun showDeleteDialog() {
        _dialogState.value = EditMenuDialogState.DELETE
    }

    fun closeDialog() {
        _dialogState.value = EditMenuDialogState.CLOSED
    }

    fun fetchMenuItems(storeId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            storeDetailRepository.getStoreDetail(storeId)
                .onSuccess { storeDetail ->
                    _uiState.update {
                        it.copy(
                            menuItems = storeDetail.menus.toPersistentList(),
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message,
                            isLoading = false
                        )
                    }
                    _sideEffect.send(EditMenuSideEffect.ShowSnackbar("메뉴 로딩 실패"))
                }
        }
    }

    fun selectMenuItem(menuItem: MenuItem) {
        selectedMenuId = menuItem.id
        _uiState.update {
            it.copy(selectedMenuItem = menuItem)
        }
    }

    fun deleteMenuItem(storeId: Long) {
        if (selectedMenuId == -1L) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            storeDetailRepository.deleteMenuItem(storeId, selectedMenuId)
                .onSuccess {
                    _uiState.update { it ->
                        it.copy(
                            deleteSuccess = true,
                            isLoading = false,
                            menuItems = it.menuItems.filter { it.id != selectedMenuId }
                                .toPersistentList()
                        )
                    }
                    _sideEffect.send(EditMenuSideEffect.NavigateToDeleteSuccess(storeId))
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message,
                            isLoading = false
                        )
                    }
                    _sideEffect.send(EditMenuSideEffect.ShowSnackbar("메뉴 삭제 실패"))
                }
        }
    }

    fun resetDeleteSuccess() {
        _uiState.update { it.copy(deleteSuccess = false) }
    }
}
