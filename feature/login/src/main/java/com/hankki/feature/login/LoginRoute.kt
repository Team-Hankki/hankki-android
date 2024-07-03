package com.hankki.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = viewModel()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginSideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.StartLogin -> {
                    // 필요 시 StartLogin 추가 처리
                }

                is LoginSideEffect.LoginSuccess -> {
                    // 필요 시 LoginSuccess 추가 처리
                }

                is LoginSideEffect.LoginError -> {
                    // 필요 시 LoginError 추가 처리
                }
            }
        }
    }

    LoginScreen(
        loginState = loginState,
        onLoginClick = { viewModel.initLoginButton(context) }
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