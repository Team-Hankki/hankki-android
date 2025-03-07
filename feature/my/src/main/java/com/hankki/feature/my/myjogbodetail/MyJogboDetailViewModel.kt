package com.hankki.feature.my.myjogbodetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.domain.my.entity.response.UserInformationEntity
import com.hankki.domain.my.repository.MyRepository
import com.hankki.domain.token.repository.TokenRepository
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyJogboDetailViewModel @Inject constructor(
    private val myRepository: MyRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _myJogboDetailState = MutableStateFlow(MyJogboDetailState())
    val myJogboDetailState: StateFlow<MyJogboDetailState>
        get() = _myJogboDetailState.asStateFlow()

    private val _mySideEffect: MutableSharedFlow<MyJogboDetailSideEffect> = MutableSharedFlow()
    val mySideEffect: SharedFlow<MyJogboDetailSideEffect>
        get() = _mySideEffect.asSharedFlow()

    fun getJogboDetail(favoriteId: Long) {
        viewModelScope.launch {
            myRepository.getJogboDetail(favoriteId)
                .onSuccess { jogbo ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        userInformation = UserInformationEntity(
                            nickname = jogbo.nickname
                        ),
                        myStoreItems = jogbo,
                        uiState = if (jogbo.stores.isEmpty()) EmptyUiState.Empty else EmptyUiState.Success(
                            jogbo.stores.toPersistentList()
                        )
                    )
                }
                .onFailure(Timber::e)
        }
    }

    fun getSharedJogboDetail(favoriteId: Long) {
        viewModelScope.launch {
            val isLogined = tokenRepository.getAccessToken().isNotEmpty()
            myRepository.getSharedJogboDetail(favoriteId)
                .onSuccess { jogbo ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        userInformation = UserInformationEntity(
                            nickname = jogbo.nickname
                        ),
                        myStoreItems = jogbo,
                        uiState = if (jogbo.stores.isEmpty()) EmptyUiState.Empty else EmptyUiState.Success(
                            jogbo.stores.toPersistentList()
                        )
                    )
                    if (!isLogined) _mySideEffect.emit(MyJogboDetailSideEffect.ShowLoginDialog)
                    else checkIsJogboOwner(favoriteId)
                }
                .onFailure { error ->
                    _myJogboDetailState.value =
                        _myJogboDetailState.value.copy(uiState = EmptyUiState.Failure)

                    if (error is HttpException && error.code() == DO_NOT_EXISTS_ERROR) {
                        if (!isLogined) {
                            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToLogin)
                        } else {
                            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToMyJogbo)
                        }
                    }
                }
        }
    }

    fun updateDeleteDialogState() {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            deleteDialogState = !_myJogboDetailState.value.deleteDialogState
        )
    }

    fun updateShareDialogState() {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            shareDialogState = !_myJogboDetailState.value.shareDialogState
        )
    }

    fun deleteSelectedStore(favoriteId: Long, storeId: Long) {
        viewModelScope.launch {
            myRepository.deleteJogboStore(favoriteId, storeId)
                .onSuccess {
                    updateDeleteDialogState()
                    getJogboDetail(favoriteId)
                    getSharedJogboDetail(favoriteId)
                }
                .onFailure(Timber::e)
        }
    }

    fun updateSelectedStoreId(storeId: Long) {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            selectedStoreId = storeId
        )
    }

    fun navigateToStoreDetail(storeId: Long) {
        viewModelScope.launch {
            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToDetail(storeId))
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _mySideEffect.emit(MyJogboDetailSideEffect.NavigateToHome)
        }
    }

    fun shareJogbo(
        context: Context,
        image: String,
        title: String,
        senderName: String,
        favoriteId: Long,
        trackResult: () -> Unit
    ) {
        val templateId = 115383L
        val templateArgs = mapOf(
            IMAGE_URL to image,
            NAME to title,
            SENDER to senderName,
            FAVORITE_ID to favoriteId.toString(),
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareCustom(context, templateId, templateArgs) { result, error ->
                if (error != null) {
                    Timber.e(error)
                } else if (result != null) {
                    context.startActivity(result.intent)
                    trackResult()
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.makeCustomUrl(templateId, templateArgs)
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
            } catch (e: UnsupportedOperationException) {
                Timber.e(e.message)
            }
        }
    }

    fun checkIsJogboOwner(favoriteId: Long) {
        viewModelScope.launch {
            myRepository.getIsJogboOwner(favoriteId)
                .onSuccess { isJogboOwner ->
                    _myJogboDetailState.value = _myJogboDetailState.value.copy(
                        isJogboOwner = isJogboOwner.isJogboOwner
                    )
                }
                .onFailure(Timber::e)
        }
    }

    fun updateLoginDialog() {
        _myJogboDetailState.value = _myJogboDetailState.value.copy(
            loginDialogState = !_myJogboDetailState.value.loginDialogState,
        )
    }

    companion object {
        private const val DO_NOT_EXISTS_ERROR: Int = 404
        private const val IMAGE_URL: String = "IMAGE_URL"
        private const val NAME: String = "NAME"
        private const val SENDER: String = "SENDER"
        private const val FAVORITE_ID: String = "FAVORITE_ID"
    }
}
