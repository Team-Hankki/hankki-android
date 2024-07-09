package com.hankki.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = hiltViewModel()
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginSideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.LoginSuccess -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }

                is LoginSideEffect.LoginError -> {
                    Toast.makeText(context, "Login Error: ${sideEffect.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LoginScreen(
        loginState = loginState,
        onLoginClick = { viewModel.startKakaoLogin(context) }
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
            Text(text = "Logged in success")
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