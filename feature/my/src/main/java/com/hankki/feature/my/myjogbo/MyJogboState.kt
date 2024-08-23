package com.hankki.feature.my.myjogbo

import com.hankki.core.common.utill.UiState
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import kotlinx.collections.immutable.PersistentList

data class MyJogboState(
    val editModeState: Boolean = false,
    val uiState: UiState<PersistentList<MyJogboModel>> = UiState.Loading,
    var dialogState: Boolean = false,
)
