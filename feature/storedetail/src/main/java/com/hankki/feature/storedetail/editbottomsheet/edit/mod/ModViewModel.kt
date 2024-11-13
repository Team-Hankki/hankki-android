package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModState())
    val uiState: StateFlow<ModState> = _uiState

    private val _dialogState = MutableStateFlow(ModDialogState.CLOSED)
    val dialogState: StateFlow<ModDialogState> = _dialogState

    private val _sideEffect = MutableSharedFlow<ModSideEffect>()
    val sideEffect: SharedFlow<ModSideEffect> = _sideEffect

    private var _currentStoreId: Long = -1
    private var _currentMenuId: Long = -1

    private var originalMenuName: String = ""
    private var originalPrice: String = ""

    fun initialize(newStoreId: Long, newMenuId: Long, menuName: String, price: String) {
        this._currentStoreId = newStoreId
        this._currentMenuId = newMenuId
        originalMenuName = menuName
        originalPrice = price

        _uiState.value = ModState(
            menuName = menuName,
            price = price,
            isPriceValid = price.toIntOrNull()?.let { it < 8000 } ?: false,
            isOverPriceLimit = price.toIntOrNull()?.let { it >= 8000 } ?: false,
            showRestoreMenuNameButton = false,
            showRestorePriceButton = false,
            isSubmitEnabled = false
        )
    }

    fun updateMenuName(newValue: String) {
        _uiState.update { state ->
            state.copy(
                menuName = newValue,
                showRestoreMenuNameButton = newValue != originalMenuName,
                isSubmitEnabled = newValue.isNotBlank() && (newValue != originalMenuName || state.price != originalPrice)
            )
        }
    }

    fun updatePrice(newValue: String) {
        val priceInt = newValue.toIntOrNull()
        _uiState.update { state ->
            state.copy(
                price = newValue,
                isPriceValid = priceInt != null && priceInt < 8000,
                isOverPriceLimit = priceInt?.let { it >= 8000 } == true,
                showRestorePriceButton = newValue != originalPrice,
                isSubmitEnabled = newValue.isNotBlank() && (state.menuName != originalMenuName || newValue != originalPrice)
            )
        }
    }

    suspend fun submitMenu() {
        val uiState = _uiState.value
        val parsedPrice = uiState.price.toIntOrNull()

        if (parsedPrice != null && parsedPrice < 8000 && uiState.isPriceValid) {
            viewModelScope.launch {
                val menuUpdateRequest = MenuUpdateRequestEntity(
                    name = uiState.menuName,
                    price = parsedPrice
                )
                storeDetailRepository.putUpdateMenu(_currentStoreId, _currentMenuId, menuUpdateRequest)
                    .onSuccess {
                        _sideEffect.emit(ModSideEffect.NavigateToEditSuccess(_currentStoreId))
                    }
                    .onFailure { error ->
                        _sideEffect.emit(ModSideEffect.MenuAddFailure(error.message ?: "Unknown error"))
                    }
            }
        } else {
            _sideEffect.emit(ModSideEffect.MenuAddFailure("8000이하만 입력 가능"))
        }
    }

    fun deleteMenu() {
        viewModelScope.launch {
            storeDetailRepository.deleteMenuItem(_currentStoreId, _currentMenuId)
                .onSuccess {
                    _sideEffect.emit(ModSideEffect.NavigateToDeleteSuccess(_currentStoreId))
                }
                .onFailure { error ->
                    _sideEffect.emit(ModSideEffect.MenuAddFailure(error.message ?: "Unknown error"))
                }
        }
    }

    fun updateMenuFieldFocus(focused: Boolean) {
        _uiState.update { state ->
            state.copy(
                isMenuFieldFocused = focused,
                isPriceFieldFocused = if (focused) false else state.isPriceFieldFocused
            )
        }
    }

    fun updatePriceFieldFocus(focused: Boolean) {
        _uiState.update { state ->
            state.copy(
                isPriceFieldFocused = focused,
                isMenuFieldFocused = if (focused) false else state.isMenuFieldFocused
            )
        }
    }

    fun closeDialog() {
        _dialogState.value = ModDialogState.CLOSED
    }

    fun showDeleteDialog() {
        _dialogState.value = ModDialogState.DELETE
    }
}
