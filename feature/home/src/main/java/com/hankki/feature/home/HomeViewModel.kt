package com.hankki.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.domain.home.repository.HomeRepository
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.MarkerItem
import com.hankki.feature.home.model.StoreItemModel
import com.hankki.feature.home.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<HomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<HomeSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        getStoreItems()
        getMarkerItems()
    }

    fun getUniversityInformation() {
        viewModelScope.launch {
            homeRepository.getMyUniversity()
                .onSuccess { university ->
                    _state.value = _state.value.copy(
                        myUniversityModel = university.toModel()
                    )
                    moveMap(
                        _state.value.myUniversityModel.latitude,
                        _state.value.myUniversityModel.longitude
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun moveMap(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.MoveMap(latitude, longitude))
        }
    }

    fun getStoreItems() {
        viewModelScope.launch {
            homeRepository.getStores(
                universityId = _state.value.myUniversityModel.id,
                storeCategory = if (_state.value.categoryChipState is ChipState.Fixed) {
                    (_state.value.categoryChipState as ChipState.Fixed).tag
                } else null,
                priceCategory = if (_state.value.priceChipState is ChipState.Fixed) {
                    (_state.value.priceChipState as ChipState.Fixed).tag
                } else null,
                sortOption = if (_state.value.sortChipState is ChipState.Fixed) {
                    (_state.value.sortChipState as ChipState.Fixed).tag
                } else null
            ).onSuccess { stores ->
                _state.value = _state.value.copy(
                    storeItems = stores.map { it.toModel() }.toPersistentList()
                )
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun getJogboItems() {
        _state.value = _state.value.copy(
            jogboItems = persistentListOf(
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천 학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = true
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = false
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = true
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = false
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = true
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "학교 5년째 다니는 화석의 추천",
                    tags = persistentListOf("#미친가성비", "#꼭가보세요"),
                    isReported = false
                )
            )
        )
    }

    fun getMarkerItems() {
        _state.value = _state.value.copy(
            markerItems = persistentListOf(
                MarkerItem(
                    x = 37.3009489417651,
                    y = 127.03549529577874,
                    title = "한끼네 한정식",
                    id = 1
                ),
                MarkerItem(
                    x = 37.3005489417651,
                    y = 127.03549529577874,
                    title = "두끼네 두정식",
                    id = 2
                ),
                MarkerItem(
                    x = 37.2909489417651,
                    y = 127.03529529577874,
                    title = "세끼네 세정식",
                    id = 3
                ),
                MarkerItem(
                    x = 37.2909489417651,
                    y = 127.03569529577874,
                    title = "네끼네 네정식",
                    id = 4
                ),
            )
        )
    }

    fun clickMarkerItem(id: Int) {
        // id를 통해 서버통신하여 Store 정보를 받아올 예정
        _state.value = _state.value.copy(
            isMainBottomSheetOpen = false,
            selectedStoreItem = StoreItemModel(
                imageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                category = "한식",
                name = "한끼네 한정식",
                lowerPrice = 7900,
                heartCount = 300
            )
        )
    }

    fun clickMap() {
        _state.value = _state.value.copy(
            isMainBottomSheetOpen = true
        )
    }

    fun clickCategoryChip() {
        viewModelScope.launch {
            homeRepository.getCategories()
                .onSuccess { chips ->
                    _state.value = _state.value.copy(
                        categoryChipState = when (_state.value.categoryChipState) {
                            is ChipState.Fixed -> ChipState.Unselected()
                            is ChipState.Selected -> ChipState.Unselected()
                            is ChipState.Unselected -> ChipState.Selected()
                        },
                        categoryChipItems = chips.map {
                            CategoryChipItem(
                                name = it.name,
                                tag = it.tag,
                                imageUrl = it.imageUrl
                            )
                        }.toPersistentList()
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun selectCategoryChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Fixed(item, tag)
        )
    }

    fun dismissCategoryChip() {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Unselected()
        )
    }

    fun clickPriceChip() {
        viewModelScope.launch {
            homeRepository.getPriceCategories()
                .onSuccess { chips ->
                    _state.value = _state.value.copy(
                        priceChipState = when (_state.value.priceChipState) {
                            is ChipState.Fixed -> ChipState.Unselected()
                            is ChipState.Selected -> ChipState.Unselected()
                            is ChipState.Unselected -> ChipState.Selected()
                        },
                        priceChipItems = chips.map {
                            ChipItem(
                                name = it.name,
                                tag = it.tag
                            )
                        }.toPersistentList()
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun selectPriceChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Fixed(item, tag)
        )
    }

    fun dismissPriceChip() {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Unselected()
        )
    }

    fun clickSortChip() {
        viewModelScope.launch {
            homeRepository.getSortCategories()
                .onSuccess { chips ->
                    _state.value = _state.value.copy(
                        sortChipState = when (_state.value.sortChipState) {
                            is ChipState.Fixed -> ChipState.Unselected()
                            is ChipState.Selected -> ChipState.Unselected()
                            is ChipState.Unselected -> ChipState.Selected()
                        },
                        sortChipItems = chips.map {
                            ChipItem(
                                name = it.name,
                                tag = it.tag
                            )
                        }.toPersistentList()
                    )
                }.onFailure { error ->
                    Timber.e(error)
                }
        }
    }

    fun selectSortChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            sortChipState = ChipState.Fixed(item, tag)
        )
    }

    fun dismissSortChip() {
        _state.value = _state.value.copy(
            sortChipState = ChipState.Unselected()
        )
    }

    fun controlMyJogboBottomSheet() {
        _state.value = _state.value.copy(
            isMyJogboBottomSheetOpen = !_state.value.isMyJogboBottomSheetOpen
        )
    }
}
