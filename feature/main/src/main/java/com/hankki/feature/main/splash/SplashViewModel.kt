package com.hankki.feature.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private val _sideEffects = MutableSharedFlow<SplashSideEffect>()
    val sideEffects: SharedFlow<SplashSideEffect> get() = _sideEffects.asSharedFlow()

    fun showSplash() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            // 로컬이랑 통신 -> 엑세스, 리프레시가 있는지. 진짜 있는지만.
            // 검사해서 isEMpty -> boolean 이걸 변수에 담아.
            val isLogined = true // 여기에 할당

            if (isLogined) { // 토큰이 있다면 홈으로 이동
                _sideEffects.emit(SplashSideEffect.NavigateToHome)
            } else { // 토큰이 없다면 로그인으로 이동
                _sideEffects.emit(SplashSideEffect.NavigateLogin)
            }
        }
    }

    companion object {
        const val SPLASH_DURATION = 1200L
    }
}
