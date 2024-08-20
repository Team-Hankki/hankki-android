package com.hankki.feature.my.newjogbo

sealed class NewJogboSideEffect {
 data object NavigateToNewJogbo : NewJogboSideEffect()
 data object ShowErrorDialog : NewJogboSideEffect()
}
