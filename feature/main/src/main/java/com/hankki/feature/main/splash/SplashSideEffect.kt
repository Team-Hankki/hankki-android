package com.hankki.feature.main.splash

sealed class SplashSideEffect {
    data object NavigateLogin : SplashSideEffect()
    data object NavigateToHome : SplashSideEffect()
}
