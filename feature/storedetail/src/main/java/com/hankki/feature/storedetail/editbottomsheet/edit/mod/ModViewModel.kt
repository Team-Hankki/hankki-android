package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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

    var storeId: Long = -1
    private var menuId: Long = -1

    fun initialize(storeId: Long, menuId: Long, menuName: String, price: String) {
        this.storeId = storeId
        this.menuId = menuId
        _uiState.value = ModState(
            menuNameFieldValue = TextFieldValue(menuName, selection = TextRange(menuName.length)),
            priceFieldValue = TextFieldValue(price, selection = TextRange(price.length)),
            isPriceValid = price.toIntOrNull()?.let { it < 8000 } ?: false,
            isOverPriceLimit = price.toIntOrNull()?.let { it >= 8000 } ?: false
        )
    }

    fun updateMenuName(newValue: TextFieldValue) {
        _uiState.value = _uiState.value.copy(
            menuNameFieldValue = newValue,
            showRestoreMenuNameButton = newValue.text != _uiState.value.menuNameFieldValue.text
        )
    }

    fun updatePrice(newValue: TextFieldValue) {
        val priceInt = newValue.text.toIntOrNull()
        _uiState.value = _uiState.value.copy(
            priceFieldValue = newValue,
            isOverPriceLimit = priceInt?.let { it >= 8000 } == true,
            isPriceValid = priceInt != null && priceInt < 8000,
            showRestorePriceButton = newValue.text != _uiState.value.priceFieldValue.text
        )
    }

    suspend fun submitMenu() {
        val uiState = _uiState.value
        val parsedPrice = uiState.priceFieldValue.text.toIntOrNull()

        if (parsedPrice != null && parsedPrice < 8000 && uiState.isPriceValid) {
            viewModelScope.launch {
                val menuUpdateRequest = MenuUpdateRequestEntity(
                    name = uiState.menuNameFieldValue.text,
                    price = parsedPrice
                )
                storeDetailRepository.putUpdateMenu(storeId, menuId, menuUpdateRequest)
                    .onSuccess {
                        _sideEffect.emit(ModSideEffect.NavigateToEditSuccess(storeId))
                    }
                    .onFailure { error ->
                        _sideEffect.emit(ModSideEffect.MenuAddFailure(error.message ?: "Unknown error"))
                    }
            }
        } else {
            _sideEffect.emit(ModSideEffect.MenuAddFailure("Price must be below 8000"))
        }
    }

    fun deleteMenu() {
        viewModelScope.launch {
            storeDetailRepository.deleteMenuItem(storeId, menuId)
                .onSuccess {
                    _sideEffect.emit(ModSideEffect.NavigateToDeleteSuccess(storeId))
                }
                .onFailure { error ->
                    _sideEffect.emit(ModSideEffect.MenuAddFailure(error.message ?: "Unknown error"))
                }
        }
    }

    fun closeDialog() {
        _dialogState.value = ModDialogState.CLOSED
    }

    fun showDeleteDialog() {
        _dialogState.value = ModDialogState.DELETE
    }


}
