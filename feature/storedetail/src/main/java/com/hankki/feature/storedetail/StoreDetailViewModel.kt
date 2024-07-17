package com.hankki.feature.storedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.UiState
import com.hankki.domain.storedetail.entity.StoreDetailHeartsResponseEntity
import com.hankki.domain.storedetail.entity.StoreDetailResponseEntity
import com.hankki.domain.storedetail.repository.StoreDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreDetailViewModel @Inject constructor(
    private val storeDetailRepository: StoreDetailRepository
) : ViewModel() {

    private val _storeState = MutableStateFlow(
        StoreState(
            buttonLabels = persistentListOf(
                "식당이 사라졌어요",
                "더이상 8000원이하인 메뉴가 없어요",
                "부적절한 제보에요"
            )
        )
    )
    val storeState: StateFlow<StoreState> get() = _storeState.asStateFlow()

    fun fetchStoreDetail(storeId: Long) {
        viewModelScope.launch {
            _storeState.value = _storeState.value.copy(storeDetail = UiState.Loading)
            val result = storeDetailRepository.getStoreDetail(storeId)
            result.onSuccess {
                setStoreDetail(it)
            }.onFailure {
                _storeState.value = _storeState.value.copy(storeDetail = UiState.Failure)
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
            }.onFailure { exception ->
                // 에러 처리 로직 추가
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
}
