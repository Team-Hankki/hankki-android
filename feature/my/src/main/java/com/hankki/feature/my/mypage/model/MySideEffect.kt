package com.hankki.feature.my.mypage.model

sealed class MySideEffect {
    data object ShowLogoutSuccess : MySideEffect()
    data object ShowDeleteWithdrawSuccess : MySideEffect()
    data class ShowWebView(val type: String) : MySideEffect()
}
