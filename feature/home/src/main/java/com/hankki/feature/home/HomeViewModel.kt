package com.hankki.feature.home

import androidx.lifecycle.ViewModel
import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.designsystem.ChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<HomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<HomeSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun getJogboItems() {
        _state.value = _state.value.copy(
            jogboItems = persistentListOf(
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = listOf("#미친가성비", "#꼭가보세요")
                )
            )
        )
    }

    fun clickCategoryChip() {
        _state.value = _state.value.copy(
            categoryChipState = when (_state.value.categoryChipState) {
                ChipState.UNSELECTED -> ChipState.SELECTED
                ChipState.SELECTED -> ChipState.UNSELECTED
                ChipState.FIXED -> ChipState.UNSELECTED
            },
            categoryChipItems = persistentListOf(
                "한식",
                "중식",
                "일식",
                "양식",
                "분식",
                "패스트푸드",
                "디저트",
                "카페",
                "기타"
            )
        )
    }

    fun selectCategoryChipItem(item: String) {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.FIXED.apply { title = item },
        )
    }

    fun dismissCategoryChip() {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.UNSELECTED
        )
    }

    fun clickPriceChip() {
        _state.value = _state.value.copy(
            priceChipState = when (_state.value.priceChipState) {
                ChipState.UNSELECTED -> ChipState.SELECTED
                ChipState.SELECTED -> ChipState.UNSELECTED
                ChipState.FIXED -> ChipState.UNSELECTED
            },
            priceChipItems = persistentListOf(
                "6000원 이하",
                "6000 ~ 8000원"
            )
        )
    }

    fun selectPriceChipItem(item: String) {
        _state.value = _state.value.copy(
            priceChipState = ChipState.FIXED.apply { title = item },
        )
    }

    fun dismissPriceChip() {
        _state.value = _state.value.copy(
            priceChipState = ChipState.UNSELECTED
        )
    }

    fun clickSortChip() {
        _state.value = _state.value.copy(
            sortChipState = when (_state.value.sortChipState) {
                ChipState.UNSELECTED -> ChipState.SELECTED
                ChipState.SELECTED -> ChipState.UNSELECTED
                ChipState.FIXED -> ChipState.UNSELECTED
            },
            sortChipItems = persistentListOf(
                "최신순",
                "가격 낮은순",
                "추천순"
            )
        )
    }

    fun selectSortChipItem(item: String) {
        _state.value = _state.value.copy(
            sortChipState = ChipState.FIXED.apply { title = item },
        )
    }

    fun dismissSortChip() {
        _state.value = _state.value.copy(
            sortChipState = ChipState.UNSELECTED
        )
    }

    fun controlMyJogboBottomSheet() {
        _state.value = _state.value.copy(
            isMyJogboBottomSheetOpen = !_state.value.isMyJogboBottomSheetOpen
        )
    }
}
