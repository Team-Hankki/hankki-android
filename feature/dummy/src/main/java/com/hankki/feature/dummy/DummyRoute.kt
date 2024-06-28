package com.hankki.feature.dummy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.AsyncImage
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.layout.CircleLoadingScreen
import com.hankki.domain.dummy.entity.response.ReqresUserModel
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DummyRoute(
    viewModel: DummyViewModel = hiltViewModel(),
    onShowErrorSnackBar: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = true) {
        viewModel.getUserList()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is DummySideEffect.SnackBar -> onShowErrorSnackBar(sideEffect.message)
            }
        }
    }

    when (state.users) {
        UiState.Failure -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Failed ",
                    fontSize = 24.sp
                )
            }
        }

        UiState.Loading -> {
            CircleLoadingScreen()
        }

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
        item {
            Text(
                text = "Dummy Screen",
                fontSize = 24.sp
            )
        }
        items(userList) { user ->
            UserItem(user)
        }
    }
}

@Composable
fun UserItem(user: ReqresUserModel) {
    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = user.avatar,
                contentDescription = "avatar"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = user.firstName + " " + user.lastName)
                Text(text = user.email)
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserItem() {
    val user = ReqresUserModel(
        avatar = "https://reqres.in/img/faces/6-image.jpg",
        email = "pdm001125@naver.com",
        firstName = "Dongmin",
        id = 1,
        lastName = "Park"
    )
    UserItem(user = user)
}
