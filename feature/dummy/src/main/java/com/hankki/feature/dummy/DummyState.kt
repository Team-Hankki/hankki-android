package com.hankki.feature.dummy

import com.hankki.core.common.utill.UiState
import com.hankki.domain.dummy.entity.response.ReqresUserModel
import kotlinx.collections.immutable.PersistentList

data class DummyState(
    var users: UiState<PersistentList<ReqresUserModel>> = UiState.Loading,
)
