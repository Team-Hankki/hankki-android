package com.hankki.feature.storedetail

sealed class StoreDetailSideEffect {
    data object NavigateUp : StoreDetailSideEffect()
    data object NavigateToAddNewJogbo : StoreDetailSideEffect()
    data object ShowTextSnackBar : StoreDetailSideEffect()
}
