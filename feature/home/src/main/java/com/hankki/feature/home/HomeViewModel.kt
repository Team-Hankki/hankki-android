package com.hankki.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.home.entity.response.CategoriesEntity
import com.hankki.domain.home.entity.response.CategoryEntity
import com.hankki.domain.home.repository.HomeRepository
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.HomeChips
import com.hankki.feature.home.model.StoreItemModel
import com.hankki.feature.home.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import timber.log.Timber.Forest.tag
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
        getUniversityInformation()

        viewModelScope.launch {
            homeRepository.getCategories().onSuccess { items ->
                _state.update {
                    it.copy(
                        categoryChipItems = items.map { chip ->
                            CategoryChipItem(
                                name = chip.name,
                                tag = chip.tag,
                                imageUrl = chip.imageUrl
                            )
                        }.toPersistentList()
                    )
                }
            }

            homeRepository.getPriceCategories().onSuccess { items ->
                _state.update {
                    it.copy(
                        priceChipItems = items.map { chip ->
                            ChipItem(
                                name = chip.name,
                                tag = chip.tag
                            )
                        }.toPersistentList()
                    )
                }
            }

            homeRepository.getSortCategories().onSuccess { items ->
                _state.update {
                    it.copy(
                        sortChipItems = items.map { chip ->
                            ChipItem(
                                name = chip.name,
                                tag = chip.tag
                            )
                        }.toPersistentList()
                    )
                }
            }
        }
    }

    private fun getUniversityInformation() {
        viewModelScope.launch {
            homeRepository.getMyUniversity()
                .onSuccess { university ->
                    if (university.id == _state.value.myUniversityModel.id) {
                        fetchData(university.id)
                        return@launch
                    }

                    clearState()

                    _state.value = _state.value.copy(
                        myUniversityModel = university.toModel()
                    )

                    fetchData(university.id)

                    moveCameraWhenUniversityNull()
                }.onFailure { error ->
                    fetchData()

                    moveCameraWhenUniversityNull()

                    Timber.e(error)
                }
        }
    }

    private fun moveCameraWhenUniversityNull() {
        if (state.value.myUniversityModel.id == null) {
            moveMyLocation()
        } else {
            moveMap(
                state.value.myUniversityModel.latitude,
                state.value.myUniversityModel.longitude
            )
        }
    }

    private fun fetchData(universityId: Long? = state.value.myUniversityModel.id) {
        getStoreItems(universityId)
        getMarkerItems(universityId)
    }

    private fun clearState() {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Unselected(),
            priceChipState = ChipState.Unselected(),
            sortChipState = ChipState.Unselected(),
            isMainBottomSheetOpen = true,
            selectedStoreItem = null
        )
    }

    fun moveMap(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.MoveMap(latitude, longitude))
        }
    }

    fun moveMyLocation() {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.MoveMyLocation)
        }
    }

    private fun getStoreItems(universityId: Long?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                storeItems = EmptyUiState.Loading
            )

            homeRepository.getStores(
                universityId = universityId,
                storeCategory = if (_state.value.categoryChipState is ChipState.Fixed) {
                    if ((_state.value.categoryChipState as ChipState.Fixed).tag == "ALL") null
                    else (_state.value.categoryChipState as ChipState.Fixed).tag
                } else null,
                priceCategory = if (_state.value.priceChipState is ChipState.Fixed) {
                    (_state.value.priceChipState as ChipState.Fixed).tag
                } else null,
                sortOption = if (_state.value.sortChipState is ChipState.Fixed) {
                    (_state.value.sortChipState as ChipState.Fixed).tag
                } else null
            ).onSuccess { stores ->
                _state.value = _state.value.copy(
                    storeItems = if (stores.isEmpty()) {
                        EmptyUiState.Empty
                    } else {
                        EmptyUiState.Success(
                            stores.map { it.toModel() }.toPersistentList()
                        )
                    }
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    storeItems = EmptyUiState.Failure
                )

                Timber.e(error)
            }
        }
    }

    fun getJogboItems(storeId: Long) {
        viewModelScope.launch {
            homeRepository.getFavorites(storeId)
                .onSuccess { jogboItems ->
                    _state.value = _state.value.copy(
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
                }.onFailure(Timber::e)
        }
    }

    private fun getMarkerItems(universityId: Long?) {
        viewModelScope.launch {
            homeRepository.getStoresPins(
                universityId = universityId,
                storeCategory = if (_state.value.categoryChipState is ChipState.Fixed) {
                    if ((_state.value.categoryChipState as ChipState.Fixed).tag == "ALL") null
                    else (_state.value.categoryChipState as ChipState.Fixed).tag
                } else null,
                priceCategory = if (_state.value.priceChipState is ChipState.Fixed) {
                    (_state.value.priceChipState as ChipState.Fixed).tag
                } else null,
                sortOption = if (_state.value.sortChipState is ChipState.Fixed) {
                    (_state.value.sortChipState as ChipState.Fixed).tag
                } else null
            ).onSuccess { pins ->
                _state.value = _state.value.copy(
                    markerItems = pins.map { it.toModel() }.toPersistentList()
                )
            }.onFailure(Timber::e)
        }
    }

    fun clickMarkerItem(id: Long) {
        viewModelScope.launch {
            homeRepository.getStoreThumbnail(id)
                .onSuccess { store ->
                    _state.value = _state.value.copy(
                        isMainBottomSheetOpen = false,
                    )
                    selectStoreItem(store.toModel())
                }.onFailure(Timber::e)
        }
    }

    fun selectStoreItem(storeItem: StoreItemModel) {
        _state.value = _state.value.copy(
            selectedStoreItem = storeItem
        )
    }

    fun clickMap() {
        _state.value = _state.value.copy(
            isMainBottomSheetOpen = true,
            selectedStoreItem = null
        )
    }

    fun controlFilterBottomSheetState() {
        _state.update {
            it.copy(
                isFilterBottomSheetOpen = !it.isFilterBottomSheetOpen
            )
        }
    }

    fun selectHomeChipItem(chip: HomeChips, item: String, tag: String) {
        runBlocking {
            when (chip) {
                HomeChips.CATEGORY -> selectCategoryChipItem(item, tag)
                HomeChips.SORT -> selectSortChipItem(item, tag)
                HomeChips.PRICE -> selectPriceChipItem(item, tag)
            }

            fetchData()
        }
    }

    fun setChipItem(priceItem: String, priceTag: String, sortItem: String, sortTag: String) {
        runBlocking {
            selectPriceChipItem(priceItem, priceTag)
            selectSortChipItem(sortItem, sortTag)

            fetchData()
        }
    }

    private fun selectCategoryChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Fixed(item, tag)
        )

        fetchData()
    }

    private fun selectPriceChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Fixed(item, tag)
        )

        fetchData()
    }

    private fun selectSortChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            sortChipState = ChipState.Fixed(item, tag)
        )
    }

    fun controlMyJogboBottomSheet() {
        clearChipFocus()
        _state.value = _state.value.copy(
            isMyJogboBottomSheetOpen = !_state.value.isMyJogboBottomSheetOpen
        )
    }

    fun addStoreAtJogbo(favoriteId: Long, storeId: Long) {
        viewModelScope.launch {
            homeRepository.addStoreAtJogbo(favoriteId, storeId)
                .onSuccess {
                    _sideEffect.emit(HomeSideEffect.ShowSnackBar("나의 족보에 추가되었습니다.", favoriteId))
                }.onFailure(Timber::e)
        }
    }

    fun setDialog(isOpen: Boolean) {
        _state.value = _state.value.copy(
            isOpenDialog = isOpen
        )
    }

    fun clearChipFocus() {
        _state.value = _state.value.copy(
            categoryChipState = if (_state.value.categoryChipState is ChipState.Selected) {
                ChipState.Unselected()
            } else {
                _state.value.categoryChipState
            },
            priceChipState = if (_state.value.priceChipState is ChipState.Selected) {
                ChipState.Unselected()
            } else {
                _state.value.priceChipState
            },
            sortChipState = if (_state.value.sortChipState is ChipState.Selected) {
                ChipState.Unselected()
            } else {
                _state.value.sortChipState
            }
        )
    }
}
