package com.hankki.feature.storedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.storedetail.entity.MenuUpdateRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailMenuAddRequestEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import com.hankki.feature.storedetail.editbottomsheet.add.MenuUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository,
) : ViewModel() {
    private val _storeState = MutableStateFlow(
        StoreDetailState(
            buttonLabels = persistentListOf(
                "식당이 사라졌어요",
                "더이상 8,000원이하인 메뉴가 없어요",
                "부적절한 제보에요"
            )
        )
    )

    val storeState: StateFlow<StoreDetailState> get() = _storeState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<StoreDetailSideEffect>()
    val sideEffects: SharedFlow<StoreDetailSideEffect> get() = _sideEffects

    private val _dialogState = MutableStateFlow(StoreDetailDialogState.CLOSED)
    val dialogState: StateFlow<StoreDetailDialogState> = _dialogState

    fun fetchStoreDetail(storeId: Long) {
        viewModelScope.launch {
            _storeState.value = _storeState.value.copy(
                storeId = storeId,
                storeDetail = UiState.Loading
            )

            storeDetailRepository.getStoreDetail(storeId).onSuccess {
                setStoreDetail(it)
            }.onFailure { error ->
                _storeState.value = _storeState.value.copy(storeDetail = UiState.Failure)

                if (error is HttpException && error.code() == DO_NOT_EXISTS_ERROR) {
                    _sideEffects.emit(StoreDetailSideEffect.ShowTextSnackBar)
                }
            }
        }
    }

    fun fetchNickname() {
        viewModelScope.launch {
            val result = storeDetailRepository.getStoreDetailNickname()
            result.onSuccess {
                _storeState.value = _storeState.value.copy(nickname = it.nickname)
            }.onFailure {
                //fail
            }
        }
    }

    private fun setStoreDetail(storeDetail: StoreDetailResponseEntity) {
        Timber.d("StoreDetail menus: ${storeDetail.menus}")
        _storeState.value = _storeState.value.copy(
            storeDetail = UiState.Success(storeDetail),
            isLiked = storeDetail.isLiked,
            heartCount = storeDetail.heartCount,
            menuItems = storeDetail.menus.toPersistentList()
        )
    }

    fun toggleLike(storeId: Long) {
        viewModelScope.launch {
            val currentState = _storeState.value
            val newLikeStatus = !currentState.isLiked

            val result = if (newLikeStatus) {
                storeDetailRepository.postStoreDetailHearts(storeId)
            } else {
                storeDetailRepository.deleteStoreDetailHearts(storeId)
            }

            result.onSuccess { response ->
                updateHeartStatus(response)
            }.onFailure {
                Timber.e("Failed to update heart status: ${it.message}")
            }
        }
    }

    private fun updateHeartStatus(response: StoreDetailHeartsResponseEntity) {
        _storeState.value = _storeState.value.copy(
            isLiked = response.isHearted,
            heartCount = response.count
        )
    }

    fun updateSelectedIndex(index: Int) {
        _storeState.value = _storeState.value.copy(selectedIndex = index)
    }

    fun controlMyJogboBottomSheet() {
        _storeState.value = _storeState.value.copy(
            isOpenJogboBottomSheet = !_storeState.value.isOpenJogboBottomSheet
        )
        if (_storeState.value.isOpenJogboBottomSheet) {
            getJogboItems(_storeState.value.storeId)
        }
    }

    private fun getJogboItems(storeId: Long) {
        viewModelScope.launch {
            storeDetailRepository.getFavorites(storeId)
                .onSuccess { jogboItems ->
                    _storeState.value = _storeState.value.copy(
                        jogboItems = jogboItems.map {
                            JogboResponseModel(
                                id = it.id,
                                title = it.title,
                                imageType = it.imageType,
                                details = it.details,
                                isAdded = it.isAdded
                            )
                        }.toPersistentList()
                    )
                }.onFailure { error ->
                    Timber.e("Failed to fetch Jogbo items: ${error.message}")
                }
        }
    }

    fun addStoreAtJogbo(favoriteId: Long, storeId: Long) {
        viewModelScope.launch {
            storeDetailRepository.addStoreAtJogbo(favoriteId, storeId)
                .onSuccess {
                    Timber.d("Store added to Jogbo successfully")
                }.onFailure { error ->
                    Timber.e("Failed to add store to Jogbo: ${error.message}")
                }
        }
    }

    fun resetSelectedIndex() {
        _storeState.value = _storeState.value.copy(selectedIndex = -1)
    }

    fun showReportConfirmation() {
        _dialogState.value = StoreDetailDialogState.REPORT_CONFIRMATION
    }

    fun showThankYouDialog() {
        _dialogState.value = StoreDetailDialogState.REPORT
    }

    fun closeDialog() {
        _dialogState.value = StoreDetailDialogState.CLOSED
    }

    fun deleteStoreDetail(storeId: Long) {
        viewModelScope.launch {
            storeDetailRepository.deleteStoreDetail(storeId)
                .onSuccess {
                    Timber.d("Store delete successfully")
                }.onFailure { error ->
                    Timber.e("Failed to delete store123: ${error.message}")
                }
        }
    }

    fun controlEditMenuBottomSheet() {
        _storeState.value = _storeState.value.copy(
            isOpenEditMenuBottomSheet = !_storeState.value.isOpenEditMenuBottomSheet
        )
    }
    fun updateMenu(storeId: Long, menuId: Long, updatedName: String, updatedPrice: Int) {
        viewModelScope.launch {
            try {
                val menuUpdateRequest = MenuUpdateRequestEntity(name = updatedName, price = updatedPrice)
                storeDetailRepository.putUpdateMenu(storeId, menuId, menuUpdateRequest)
                    .onSuccess {
                        Timber.d("Menu update 성공")
                    }
                    .onFailure {
                        Timber.e("Menu update 실패: ${it.message}")
                    }
            } catch (e: Exception) {
                Timber.e("Menu update 중 오류 발생: ${e.message}")
            }
        }
    }

    var selectedMenuId: Long = -1

    fun showDialog() {
        _dialogState.value = StoreDetailDialogState.DELETE
    }

    fun resetDeleteSuccess() {
        _storeState.value = _storeState.value.copy(deleteSuccess = false)
    }

    fun deleteMenuItem(storeId: Long, menuId: Long) {
        viewModelScope.launch {
            Timber.d("Deleting menu item - storeId: $storeId, menuId: $menuId")
            storeDetailRepository.deleteMenuItem(storeId, menuId)
                .onSuccess {
                    Timber.d("Menu item deleted successfully")
                    _storeState.value = _storeState.value.copy(deleteSuccess = true)
                    Timber.d("DeleteSuccess state updated: ${_storeState.value.deleteSuccess}")
                }.onFailure { error ->
                    Timber.e("Failed to delete menu item: ${error.message}")
                }
        }
    }

    private val _storeDetailState = MutableStateFlow(
        StoreDetailState(
            menuList = listOf(MenuUiState()),
            buttonEnabled = false
        )
    )
    val storeDetailState = _storeDetailState.asStateFlow()

    fun changeMenuName(index: Int, name: String) {
        val currentList = _storeDetailState.value.menuList.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = currentList[index].copy(name = name)
            _storeDetailState.value = _storeDetailState.value.copy(
                menuList = currentList,
                buttonEnabled = validateInputs(currentList)
            )
        }
    }

    fun changePrice(index: Int, price: String) {
        val currentList = _storeDetailState.value.menuList.toMutableList()
        if (index in currentList.indices) {
            val isPriceError = price.isNotBlank() && (price.toIntOrNull() == null || price.toIntOrNull() ?: 0 <= 0)
            currentList[index] = currentList[index].copy(
                price = price,
                isPriceError = isPriceError
            )
            _storeDetailState.value = _storeDetailState.value.copy(
                menuList = currentList,
                buttonEnabled = validateInputs(currentList)
            )
        }
    }

    fun deleteMenu(index: Int) {
        val currentList = _storeDetailState.value.menuList.toMutableList()
        if (index in currentList.indices && currentList.size > 1) {
            currentList.removeAt(index)
            _storeDetailState.value = _storeDetailState.value.copy(
                menuList = currentList,
                buttonEnabled = validateInputs(currentList)
            )
        }
    }

    fun addMenu() {
        val currentList = _storeDetailState.value.menuList.toMutableList()
        currentList.add(MenuUiState())
        _storeDetailState.value = _storeDetailState.value.copy(
            menuList = currentList,
            buttonEnabled = validateInputs(currentList)
        )
    }

    private fun validateInputs(menuList: List<MenuUiState>): Boolean {
        return menuList.all { menu ->
            menu.name.isNotBlank() &&
                    menu.price.isNotBlank() &&
                    !menu.isPriceError &&
                    menu.price.toIntOrNull() != null &&
                    menu.price.toIntOrNull()!! > 0
        }
    }

    fun setStoreId(id: Long) {
        _storeState.value = _storeState.value.copy(storeId = id)
    }

    fun submitMenus() {
        viewModelScope.launch {
            try {
                val storeId = _storeState.value.storeId
                if (storeId <= 0) {
                    _sideEffects.emit(StoreDetailSideEffect.MenuAddFailure("잘못된 식당 정보입니다"))
                    return@launch
                }

                val menuList = _storeDetailState.value.menuList
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
                    fetchStoreDetail(storeId)
                    _storeState.value = _storeState.value.copy(
                        submittedMenuCount = menuList.size
                    )
                    _storeDetailState.value = _storeDetailState.value.copy(
                        menuList = listOf(MenuUiState()),
                        buttonEnabled = false
                    )
                    _sideEffects.emit(StoreDetailSideEffect.MenuAddSuccess(storeId))
                }.onFailure { error ->
                    _sideEffects.emit(StoreDetailSideEffect.MenuAddFailure(error.message ?: "메뉴 추가 실패"))
                }
            } catch (e: Exception) {
                _sideEffects.emit(StoreDetailSideEffect.MenuAddFailure(e.message ?: "메뉴 추가 중 오류 발생"))
            }
        }
    }

companion object {
        private const val DO_NOT_EXISTS_ERROR: Int = 404
    }
}
