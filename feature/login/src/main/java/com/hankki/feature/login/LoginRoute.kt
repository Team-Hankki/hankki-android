package com.hankki.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.designsystem.component.snackbar.HankkiTextSnackBar
import com.hankki.core.designsystem.component.snackbar.HankkiTextSnackBarWithButton
import com.hankki.core.designsystem.component.snackbar.HankkiWhiteSnackBarWithButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = hiltViewModel()
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
        onLoginClick = { viewModel.startKakaoLogin(context) }
    )
}

@Composable
fun LoginScreen(
    loginState: LoginState,
    onLoginClick: () -> Unit
) {
    val snackbarHostState1 = remember { SnackbarHostState() }
    val snackbarHostState2 = remember { SnackbarHostState() }
    val snackbarHostState3 = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
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

                    // 임시 버튼 추가
                    Button(onClick = {
                        coroutineScope.launch {
                            snackbarHostState1.showSnackbar("")
                        }
                    }) {
                        Text(text = "Show gray900 Snackbar")
                    }

                    Button(onClick = {
                        coroutineScope.launch {
                            snackbarHostState2.showSnackbar("")
                        }
                    }) {
                        Text(text = "Show white Snackbar")
                    }

                    Button(onClick = {
                        coroutineScope.launch {
                            snackbarHostState3.showSnackbar("텍스트 수정 가능")
                        }
                    }) {
                        Text(text = "Show Text Input SnackBar")
                    }
                }

                SnackbarHost(
                    hostState = snackbarHostState1,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) { data ->
                    HankkiTextSnackBarWithButton(onClick = { /* 클릭 시 실행될 코드 */ })
                }
                SnackbarHost(
                    hostState = snackbarHostState2,
                    modifier = Modifier.align(Alignment.TopCenter)
                ) { data ->
                    HankkiWhiteSnackBarWithButton(onClick = { /* 클릭 시 실행될 코드 */ })
                }
                SnackbarHost(
                    hostState = snackbarHostState3,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) { data ->
                    HankkiTextSnackBar(message = data.visuals.message)
                }
            }
        }
    )
}
