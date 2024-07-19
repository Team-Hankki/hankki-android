package com.hankki.feature.my.mystore

import com.hankki.core.common.utill.EmptyUiState
import com.hankki.feature.my.mystore.model.MyStoreModel
import kotlinx.collections.immutable.PersistentList

data class MyStoreState (
    val uiState : EmptyUiState<PersistentList<MyStoreModel>> = EmptyUiState.Loading
)
