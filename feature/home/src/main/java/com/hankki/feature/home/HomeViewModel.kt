package com.hankki.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.designsystem.component.bottomsheet.JogboItemEntity
import com.hankki.feature.home.model.CategoryChipItem
import com.hankki.feature.home.model.ChipState
import com.hankki.feature.home.model.MarkerItem
import com.hankki.feature.home.model.StoreItemEntity
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    init {
        getUniversityInfo()
        getStoreItems()
        getMarkerItems()
    }

    fun moveMap(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.MoveMap(latitude, longitude))
        }
    }

    private fun getUniversityInfo() {
        _state.value = _state.value.copy(
            universityName = "한끼 대학교",
            latLng = LatLng(37.3009489417651, 127.03549529577874)
        )
    }

    private fun getStoreItems() {
        _state.value = _state.value.copy(
            storeItems = persistentListOf(
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                ),
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                ),
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                ),
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                ),
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                ),
                StoreItemEntity(
                    storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300
                )
            )
        )
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

    private fun getMarkerItems() {
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
            selectedStoreItem = StoreItemEntity(
                storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
                category = "한식",
                storeName = "한끼네 한정식",
                price = 7900,
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
        _state.value = _state.value.copy(
            categoryChipState = when (_state.value.categoryChipState) {
                is ChipState.Fixed -> ChipState.Unselected()
                is ChipState.Selected -> ChipState.Unselected()
                is ChipState.Unselected -> ChipState.Selected()
            },
            categoryChipItems = persistentListOf(
                CategoryChipItem(
                    name = "한식",
                    tag = "korean",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "중식",
                    tag = "chinese",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "일식",
                    tag = "japanese",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "양식",
                    tag = "western",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "분식",
                    tag = "snack",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "패스트푸드",
                    tag = "fastfood",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "디저트",
                    tag = "dessert",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "카페",
                    tag = "cafe",
                    imageUrl = "https://picsum.photos/200/300"
                ),
                CategoryChipItem(
                    name = "기타",
                    tag = "etc",
                    imageUrl = "https://picsum.photos/200/300"
                )
            )
        )
    }

    fun selectCategoryChipItem(item: String) {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Fixed(item)
        )
    }

    fun dismissCategoryChip() {
        _state.value = _state.value.copy(
            categoryChipState = ChipState.Unselected()
        )
    }

    fun clickPriceChip() {
        _state.value = _state.value.copy(
            priceChipState = when (_state.value.priceChipState) {
                is ChipState.Fixed -> ChipState.Unselected()
                is ChipState.Selected -> ChipState.Unselected()
                is ChipState.Unselected -> ChipState.Selected()
            },
            priceChipItems = persistentListOf(
                "6000원 이하",
                "6000 ~ 8000원"
            )
        )
    }

    fun selectPriceChipItem(item: String) {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Fixed(item)
        )
    }

    fun dismissPriceChip() {
        _state.value = _state.value.copy(
            priceChipState = ChipState.Unselected()
        )
    }

    fun clickSortChip() {
        _state.value = _state.value.copy(
            sortChipState = when (_state.value.sortChipState) {
                is ChipState.Fixed -> ChipState.Unselected()
                is ChipState.Selected -> ChipState.Unselected()
                is ChipState.Unselected -> ChipState.Selected()
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
            sortChipState = ChipState.Fixed(item)
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
