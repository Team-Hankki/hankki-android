package com.hankki.feature.report.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.report.entity.request.ReportStoreRequestEntity
import com.hankki.domain.report.repository.ReportRepository
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.MenuModel
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
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
) : ViewModel() {
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
        viewModelScope.launch {
            reportRepository.getReportCount().onSuccess {
                _state.value = _state.value.copy(
                    count = it.count
                )
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            reportRepository.getCategories().onSuccess {
                _state.value = _state.value.copy(
                    categoryList = it.toPersistentList()
                )
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    fun setLocation(location: LocationModel) {
        _state.value = _state.value.copy(
            location = location
        )
    }

    fun selectImageUri(uri: Uri?) {
        _state.value = _state.value.copy(
            selectedImageUri = uri
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
                MenuModel()
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

    fun submitReport() {
        viewModelScope.launch {
            reportRepository.postReport(
                image = _state.value.selectedImageUri?.toString(),
                request = ReportStoreRequestEntity(
                    name = _state.value.location.location,
                    category = _state.value.selectedCategory ?: "",
                    address = _state.value.location.address,
                    latitude = _state.value.location.latitude.toDouble(),
                    longitude = _state.value.location.longitude.toDouble(),
                    universityId = 1,
                    menus = _state.value.menuList.map {
                        ReportStoreRequestEntity.MenuEntity(
                            it.name,
                            it.price
                        )
                    }
                )
            ).onSuccess {
                _sideEffect.emit(
                    ReportSideEffect.NavigateReportFinish(
                        _state.value.count,
                        it.name,
                        it.id
                    )
                )
            }.onFailure { error ->
                Timber.e(error)
            }
        }
    }

    private fun checkButtonEnabled() {
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
