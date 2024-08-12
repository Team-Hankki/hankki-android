package com.hankki.feature.report.finish

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.domain.report.repository.ReportRepository
import com.hankki.feature.report.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
class ReportFinishViewModel @Inject constructor(
    private val reportRepository: ReportRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _state: MutableStateFlow<ReportFinishState> = MutableStateFlow(ReportFinishState())
    val state: StateFlow<ReportFinishState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<ReportFinishSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<ReportFinishSideEffect>
        get() = _sideEffect.asSharedFlow()


    init {
        getUserName()
    }

    fun setStoreInfo(
        count: Long,
        storeName: String,
        storeId: Long,
    ) {
        _state.value = _state.value.copy(
            count = count,
            storeName = storeName,
            storeId = storeId
        )
    }

    private fun getUserName() {
        viewModelScope.launch {
            reportRepository.getUserInfo().onSuccess {
                _state.value = _state.value.copy(name = it.nickname)
            }.onFailure {
            }
        }
    }

    fun controlBottomSheetState(showBottomSheet: Boolean) {
        _state.value = _state.value.copy(showBottomSheet = showBottomSheet)

        if (showBottomSheet) {
            viewModelScope.launch {
                reportRepository.getFavorites(_state.value.storeId)
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
    }

    fun navigateToStoreDetail() {
        viewModelScope.launch {
            _sideEffect.emit(ReportFinishSideEffect.NavigateToStoreDetail(_state.value.storeId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _sideEffect.emit(ReportFinishSideEffect.NavigateToHome)
        }
    }

    fun addStoreAtJogbo(favoriteId: Long, storeId: Long) {
        viewModelScope.launch {
            reportRepository.addStoreAtJogbo(favoriteId = favoriteId, storeId = storeId)
                .onSuccess {
                    _sideEffect.emit(
                        ReportFinishSideEffect.ShowSnackBar(
                            context.getString(R.string.add_store_at_jogbo),
                            favoriteId
                        )
                    )
                }.onFailure(Timber::e)
        }
    }
}
