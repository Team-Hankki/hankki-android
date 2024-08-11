package com.hankki.feature.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.loginSideEffects, lifecycleOwner) {
        viewModel.loginSideEffects
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.LoginSuccess -> {
                        if (sideEffect.isRegistered) {
                            navigateToHome()
                        } else {
                            navigateToOnboarding()
                        }
                    }

                    is LoginSideEffect.LoginError -> {
                        //LoginError 필요시 추가 동작
                    }

                    is LoginSideEffect.StartKakaoTalkLogin -> {
                        startKakaoTalkLogin(context, viewModel)
                    }

                    is LoginSideEffect.StartKakaoWebLogin -> {
                        startKakaoWebLogin(context, viewModel)
                    }
                }
            }
    }

    LoginScreen(
        onLoginClick = {
            viewModel.startKakaoLogin(
                isKakaoTalkAvailable = UserApiClient.instance.isKakaoTalkLoginAvailable(context)
            )
        }
    )
}


@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(0.16875f))
            Text(
                text = stringResource(R.string.done_worry),
                color = White,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier
                    .padding(start = 22.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.weight(0.76375f))
        }

        Image(
            painter = painterResource(id = R.drawable.btn_kakao),
            contentDescription = "Kakao Login Button",
            modifier = Modifier
                .noRippleClickable(onClick = onLoginClick)
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .navigationBarsPadding()
        )
    }
}

private fun startKakaoTalkLogin(context: Context, viewModel: LoginViewModel) {
    UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
        viewModel.handleLoginResult(token, error)
    }
}

private fun startKakaoWebLogin(context: Context, viewModel: LoginViewModel) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
        viewModel.handleLoginResult(token, error)
    }
}
