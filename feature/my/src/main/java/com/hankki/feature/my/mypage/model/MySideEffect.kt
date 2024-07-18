package com.hankki.feature.my.mypage.model

sealed class MySideEffect {
    data class ShowWebView(val type: String) : MySideEffect()
}
