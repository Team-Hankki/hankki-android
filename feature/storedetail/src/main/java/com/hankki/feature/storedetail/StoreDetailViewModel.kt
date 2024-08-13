package com.hankki.feature.storedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
                "더이상 8000원이하인 메뉴가 없어요",
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
            }.onFailure {
                _storeState.value = _storeState.value.copy(storeDetail = UiState.Failure)
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
        _storeState.value = _storeState.value.copy(
            storeDetail = UiState.Success(storeDetail),
            isLiked = storeDetail.isLiked,
            heartCount = storeDetail.heartCount
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
            isOpenBottomSheet = !_storeState.value.isOpenBottomSheet
        )
        if (_storeState.value.isOpenBottomSheet) {
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

    fun showMenuEditDialog() {
        _dialogState.value = StoreDetailDialogState.MENU_EDIT
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
}
