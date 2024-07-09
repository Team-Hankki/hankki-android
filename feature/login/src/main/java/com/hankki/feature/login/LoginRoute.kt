package com.hankki.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute() {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loginSideEffects.collectLatest { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.LoginSuccess -> {
                    //LoginSuccess 필요시 추가 동작
                }

                is LoginSideEffect.LoginError -> {
                    //LoginError 필요시 추가 동작
                }
            }
        }
    }

    LoginScreen(
        onLoginClick = { viewModel.startKakaoLogin(context) }
    )
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Spacer(modifier = Modifier.height(107.dp))
            Text(
                text = stringResource(R.string.done_worry),
                color = Gray900,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(41.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = "lottie",
                modifier = Modifier
                    .size(height = 315.dp, width = 411.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.btn_kakao),
            contentDescription = "Kakao Login Button",
            modifier = Modifier
                .noRippleClickable(onClick = onLoginClick)
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
