package com.hankki.feature.report.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.MenuModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReportViewModel @Inject constructor() : ViewModel() {
    private val _state: MutableStateFlow<ReportState> = MutableStateFlow(ReportState())
    val state: StateFlow<ReportState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<ReportSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<ReportSideEffect>
        get() = _sideEffect.asSharedFlow()

    init {
        getCount()
        getCategories()
    }

    private fun getCount() {
        _state.value = _state.value.copy(
            count = 51
        )
    }

    private fun getCategories() {
        _state.value = _state.value.copy(
            categoryList = persistentListOf(
                CategoryEntity(
                    "한식",
                    "korean",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "중식",
                    "chinese",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "일식",
                    "japanese",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "양식",
                    "western",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "분식",
                    "snack",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "디저트",
                    "dessert",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                ),
                CategoryEntity(
                    "기타",
                    "etc",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/54/food-1869622_960_720.jpg"
                )
            )
        )
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(
            location = location
        )
    }


    fun selectCategory(category: String) {
        _state.value = _state.value.copy(
            selectedCategory = category
        )
        checkButtonEnabled()
    }

    fun changeMenuName(index: Int, name: String) {
        if (_state.value.menuList.size <= index) return
        if (_state.value.menuList.isEmpty()) return

        _state.value = _state.value.copy(
            menuList = _state.value.menuList.set(
                index,
                _state.value.menuList[index].copy(name = name)
            )
        )
        checkButtonEnabled()
    }

    fun changePrice(index: Int, price: String) {
        if (_state.value.menuList.size <= index) return
        if (_state.value.menuList.isEmpty()) return

        _state.value = _state.value.copy(
            menuList = _state.value.menuList.set(
                index,
                _state.value.menuList[index].copy(
                    price = price,
                    isPriceError = (((price.takeIf { it.isNotBlank() }?.toLong() ?: 0) > 8000))
                )
            )
        )
        checkButtonEnabled()
    }

    fun addMenu() {
        _state.value = _state.value.copy(
            menuList = _state.value.menuList.add(
                MenuModel("", "")
            )
        )
        checkButtonEnabled()
    }

    fun deleteMenu(index: Int) {
        _state.value = _state.value.copy(
            menuList = _state.value.menuList.removeAt(index)
        )
        checkButtonEnabled()
    }

    fun navigateToReportFinish() {
        viewModelScope.launch {
            _sideEffect.emit(
                with(_state.value) {
                    ReportSideEffect.navigateReportFinish(
                        count = count,
                        storeName = location.location,
                        storeId = storeId
                    )
                }
            )
        }
    }

    fun checkButtonEnabled() {
        with(_state.value) {
            _state.value = _state.value.copy(
                buttonEnabled = menuList.isNotEmpty()
                        && menuList.none { it.isPriceError }
                        && menuList.none { it.name.isEmpty() }
                        && menuList.none { it.price.isEmpty() }
                        && selectedCategory != null
                        && location.location.isNotEmpty()
            )
        }
    }
}
