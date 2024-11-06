package com.hankki.feature.storedetail

sealed class StoreDetailSideEffect {
    data object NavigateUp : StoreDetailSideEffect()
    data object NavigateToAddNewJogbo : StoreDetailSideEffect()
    data object ShowTextSnackBar : StoreDetailSideEffect()
    data class MenuAddSuccess(val storeId: Long) : StoreDetailSideEffect()
    data class MenuAddFailure(val message: String) : StoreDetailSideEffect()
}
