package com.hankki.feature.storedetail.editbottomsheet.add.addmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.storedetail.entity.StoreDetailMenuAddRequestEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddMenuViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {

    private val _addMenuState = MutableStateFlow(AddMenuState())
    val addMenuState = _addMenuState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<AddMenuSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun setStoreId(storeId: Long) {
        _addMenuState.update { it.copy(storeId = storeId) }
    }

    fun onEvent(event: AddMenuIntent) {
        when (event) {
            is AddMenuIntent.OnMenuNameChange -> handleMenuNameChange(event.index, event.name)
            is AddMenuIntent.OnPriceChange -> handlePriceChange(event.index, event.price)
            is AddMenuIntent.OnDeleteMenu -> deleteMenu(event.index)
            AddMenuIntent.OnAddMenu -> addMenu()
            AddMenuIntent.OnSubmit -> submitMenus()
            AddMenuIntent.OnBackClick -> backClick()
        }
    }

    private fun handleMenuNameChange(index: Int, name: String) {
        val currentList = _addMenuState.value.menuList
        if (index in currentList.indices) {
            val updatedList = currentList.toPersistentList().set(index, currentList[index].copy(name = name))
            _addMenuState.update { state ->
                state.copy(
                    menuList = updatedList,
                    buttonEnabled = validateInputs(updatedList)
                )
            }
        }
    }

    private fun isValidPrice(price: String): Boolean {
        return price.toIntOrNull()?.let { numericPrice ->
            numericPrice in 1..8000
        } ?: false
    }

    private fun handlePriceChange(index: Int, price: String) {
        val currentList = _addMenuState.value.menuList
        if (index in currentList.indices) {
            val isPriceError = price.isNotBlank() && !isValidPrice(price)

            val updatedList = currentList.toPersistentList().set(
                index,
                currentList[index].copy(price = price, isPriceError = isPriceError)
            )
            _addMenuState.update { state ->
                state.copy(
                    menuList = updatedList,
                    buttonEnabled = validateInputs(updatedList)
                )
            }
        }
    }

    private fun validateInputs(menuList: List<MenuUiState>): Boolean {
        return menuList.all { menu ->
            menu.name.isNotBlank() &&
                    menu.price.isNotBlank() &&
                    !menu.isPriceError &&
                   isValidPrice(menu.price)
        }
    }

    private fun deleteMenu(index: Int) {
        val currentList = _addMenuState.value.menuList
        if (index in currentList.indices && currentList.size > 1) {
            val updatedList = currentList.toPersistentList().removeAt(index)
            _addMenuState.update { state ->
                state.copy(
                    menuList = updatedList,
                    buttonEnabled = validateInputs(updatedList)
                )
            }
        }
    }

    private fun addMenu() {
        val currentList = _addMenuState.value.menuList
        val updatedList = currentList.toPersistentList().add(MenuUiState())
        _addMenuState.update { state ->
            state.copy(
                menuList = updatedList,
                buttonEnabled = validateInputs(updatedList)
            )
        }
    }

    private fun submitMenus() {
        viewModelScope.launch {
            val storeId = _addMenuState.value.storeId
            if (storeId <= 0) {
                emitSideEffect(AddMenuSideEffect.ShowError("잘못된 식당 정보입니다"))
                return@launch
            }

            val menuList = _addMenuState.value.menuList
            val menuRequests = menuList.map { menu ->
                StoreDetailMenuAddRequestEntity(
                    name = menu.name,
                    price = menu.price.toInt()
                )
            }

            storeDetailRepository.postMenus(
                storeId = storeId,
                menus = menuRequests
            ).onSuccess {
                emitSideEffect(AddMenuSideEffect.NavigateToSuccess)
                _addMenuState.update { state ->
                    state.copy(
                        menuList = persistentListOf(MenuUiState()),
                        buttonEnabled = false
                    )
                }
            }.onFailure { error ->
                Timber.e(error)
                emitSideEffect(AddMenuSideEffect.ShowError(error.message ?: "메뉴 추가에 실패하였습니다. 다시 시도해 주세요."))
            }
        }
    }

    private fun backClick() {
        viewModelScope.launch {
            emitSideEffect(AddMenuSideEffect.NavigateBack)
        }
    }

    private suspend fun emitSideEffect(sideEffect: AddMenuSideEffect) {
        _sideEffect.emit(sideEffect)
    }
}
