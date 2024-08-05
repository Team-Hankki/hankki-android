package com.hankki.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.home.entity.response.CategoriesEntity
import com.hankki.domain.home.entity.response.CategoryEntity
import com.hankki.domain.home.entity.response.CategoryResponseEntity
import com.hankki.domain.home.repository.HomeRepository
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipItem
import com.hankki.feature.home.model.ChipState
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
import kotlinx.coroutines.launch
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

    fun getUniversityInformation() {
        viewModelScope.launch {
            homeRepository.getMyUniversity()
                .onSuccess { university ->
                    if (_state.value.myUniversityModel.id != university.id) {
                        _state.value = _state.value.copy(
                            myUniversityModel = university.toModel()
                        )
                        moveMap(
                            _state.value.myUniversityModel.latitude,
                            _state.value.myUniversityModel.longitude
                        )
                        fetchData()
                    }
                }.onFailure { error ->
                    fetchData()
                    Timber.e(error)
                }
        }
    }

    fun fetchData() {
        getStoreItems()
        getMarkerItems()
    }

    fun moveMap(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.MoveMap(latitude, longitude))
        }
    }

    private fun getStoreItems() {
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
            }.onFailure(Timber::e)
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

    private fun getMarkerItems() {
        viewModelScope.launch {
            homeRepository.getStoresPins(
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
            isMainBottomSheetOpen = true
        )
    }

    private fun updateChipState(
        targetChipState: ChipState,
        updateState: (ChipState) -> Unit,
        fetchItems: suspend () -> Result<List<CategoryEntity>>,
        onSuccess: (List<CategoryEntity>) -> Unit = { },
    ) {
        val newState = when (targetChipState) {
            is ChipState.Fixed -> ChipState.Unselected()
            is ChipState.Selected -> ChipState.Unselected()
            is ChipState.Unselected -> ChipState.Selected()
        }

        _state.value = _state.value.copy(
            categoryChipState = if (_state.value.categoryChipState is ChipState.Selected && targetChipState != _state.value.categoryChipState) ChipState.Unselected() else _state.value.categoryChipState,
            priceChipState = if (_state.value.priceChipState is ChipState.Selected && targetChipState != _state.value.priceChipState) ChipState.Unselected() else _state.value.priceChipState,
            sortChipState = if (_state.value.sortChipState is ChipState.Selected && targetChipState != _state.value.sortChipState) ChipState.Unselected() else _state.value.sortChipState
        )

        updateState(newState)

        if (newState is ChipState.Selected) {
            viewModelScope.launch {
                fetchItems()
                    .onSuccess { chips ->
                        onSuccess(chips)
                    }.onFailure(Timber::e)
            }
        }
    }

    fun clickCategoryChip() {
        updateChipState(
            targetChipState = _state.value.categoryChipState,
            updateState = { _state.value = _state.value.copy(categoryChipState = it) },
            fetchItems = { homeRepository.getCategories() },
        ) { chips ->
            _state.value = _state.value.copy(
                categoryChipItems = chips.map { chip ->
                    chip as CategoryResponseEntity
                    CategoryChipItem(
                        name = chip.name,
                        tag = chip.tag,
                        imageUrl = chip.imageUrl
                    )
                }.toPersistentList()
            )
        }
    }

    fun clickPriceChip() {
        updateChipState(
            targetChipState = _state.value.priceChipState,
            updateState = { _state.value = _state.value.copy(priceChipState = it) },
            fetchItems = { homeRepository.getPriceCategories() }
        ) { chips ->
            _state.value = _state.value.copy(
                priceChipItems = chips.map { chip ->
                    chip as CategoriesEntity
                    ChipItem(
                        name = chip.name,
                        tag = chip.tag
                    )
                }.toPersistentList()
            )
        }
    }

    fun clickSortChip() {
        updateChipState(
            targetChipState = _state.value.sortChipState,
            updateState = { _state.value = _state.value.copy(sortChipState = it) },
            fetchItems = { homeRepository.getSortCategories() }
        ){ chips ->
            _state.value = _state.value.copy(
                sortChipItems = chips.map { chip ->
                    chip as CategoriesEntity
                    ChipItem(
                        name = chip.name,
                        tag = chip.tag
                    )
                }.toPersistentList()
            )
        }
    }

    fun selectCategoryChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Fixed(item, tag)
        )
    }

    fun selectPriceChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Fixed(item, tag)
        )
    }

    fun selectSortChipItem(item: String, tag: String) {
        _state.value = _state.value.copy(
            sortChipState = ChipState.Fixed(item, tag)
        )
    }

    fun controlMyJogboBottomSheet() {
        _state.value = _state.value.copy(
            isMyJogboBottomSheetOpen = !_state.value.isMyJogboBottomSheetOpen
        )
    }

    fun addStoreAtJogbo(favoriteId: Long, storeId: Long) {
        viewModelScope.launch {
            homeRepository.addStoreAtJogbo(favoriteId, storeId)
                .onSuccess {

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
