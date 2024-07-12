package com.hankki.feature.report.searchstore

import androidx.lifecycle.ViewModel
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.feature.report.model.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchStoreViewModel @Inject constructor(
) : ViewModel() {
    private val _state: MutableStateFlow<SearchStoreState> = MutableStateFlow(SearchStoreState())
    val state: StateFlow<SearchStoreState>
        get() = _state.asStateFlow()

    init {
        getStores()
    }

    fun setValue(value: String) {
        _state.value = _state.value.copy(value = value)
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(selectedLocation = location)
    }

    fun getStores() { // duration을 통해 호출할 예정
        _state.value = _state.value.copy(
            uiState = EmptyUiState.Success(
                persistentListOf(
                    LocationModel(
                        location = "고동밥집 1호점",
                        address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                        longitude = 1f,
                        latitude = 1f
                    ),
                    LocationModel(
                        location = "고동밥집 1호점",
                        address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                        longitude = 2f,
                        latitude = 2f
                    ),
                    LocationModel(
                        location = "고동밥집 1호점",
                        address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                        longitude = 3f,
                        latitude = 3f
                    ),
                    LocationModel(
                        location = "고동밥집 1호점",
                        address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                        longitude = 4f,
                        latitude = 4f
                    ),
                    LocationModel(
                        location = "고동밥집 1호점",
                        address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                        longitude = 5f,
                        latitude = 5f
                    ),
                )
            )
        )
    }
}
