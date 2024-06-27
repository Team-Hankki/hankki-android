package com.hankki.feature.dummy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.utill.UiState
import com.hankki.domain.dummy.entity.response.ReqresUserModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DummyRoute(
    viewModel: DummyViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.getUserList()
    }

    when (state.users) {
        UiState.Failure -> {}
        UiState.Loading -> {}
        is UiState.Success -> {
            DummyScreen(
                userList = (state.users as UiState.Success<ImmutableList<ReqresUserModel>>).data
            )
        }
    }
}

@Composable
fun DummyScreen(userList: ImmutableList<ReqresUserModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(userList) { user ->
            Text(text = user.email)
        }
    }
}
