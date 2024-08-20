package com.hankki.feature.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.domain.token.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _sideEffects = MutableSharedFlow<SplashSideEffect>()
    val sideEffects: SharedFlow<SplashSideEffect> get() = _sideEffects.asSharedFlow()

    fun showSplash() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)

            val isLogined = tokenRepository.getAccessToken().isNotEmpty()
            if (isLogined) {
                _sideEffects.emit(SplashSideEffect.NavigateToHome)
            } else {
                _sideEffects.emit(SplashSideEffect.NavigateLogin)
            }
        }
    }

    companion object {
        const val SPLASH_DURATION = 1200L
    }
}
