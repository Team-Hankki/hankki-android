package com.hankki.feature.login

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = viewModel()
    //val loginState by viewModel.loginState.collectAsStateWithlifecycle()
    val loginState by viewModel.loginState.collectAsState()

    LoginScreen(
        loginState = loginState,
        onLoginClick = { context -> viewModel.loginWithKakaoTalk(context) }
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginClick: (Context) -> Unit
) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login")
        if (loginState.isLoggedIn) {
            Text(text = "Logged in: ${loginState.accessToken}")
        } else {
            Button(onClick = { onLoginClick(context) }) {
                Text(text = "Login with KakaoTalk")
            }
            loginState.errorMessage?.let {
                Text(text = "Error: $it")
            }
        }
    }
}