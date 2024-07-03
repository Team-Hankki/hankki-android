package com.hankki.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = viewModel()
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginSideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.StartLogin -> {
                    viewModel.loginWithKakaoTalk(context)
                }

                is LoginSideEffect.LoginSuccess -> {
                    viewModel.updateState(sideEffect)
                }

                is LoginSideEffect.LoginError -> {
                    viewModel.updateState(sideEffect)
                }
            }
        }
    }

    LoginScreen(
        loginState = loginState,
        onLoginClick = { viewModel.initLoginButton() }
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login")
        if (loginState.isLoggedIn) {
            Text(text = "Logged in: ${loginState.accessToken}")
        } else {
            Button(onClick = { onLoginClick() }) {
                Text(text = "Login with KakaoTalk")
            }
            loginState.errorMessage?.let {
                Text(text = "Error: $it")
            }
        }
    }
}