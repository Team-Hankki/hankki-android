package com.hankki.feature.dummy

import com.hankki.core.common.utill.UiState
import com.hankki.domain.dummy.entity.response.ReqresUserModel
import kotlinx.collections.immutable.ImmutableList

data class DummyState(
    var users: UiState<ImmutableList<ReqresUserModel>> = UiState.Loading,
)
