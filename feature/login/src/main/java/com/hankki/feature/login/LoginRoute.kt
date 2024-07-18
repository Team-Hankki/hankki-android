package com.hankki.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.SystemBarColorChanger
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    navigateToOnboarding: () -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.loginSideEffects, lifecycleOwner) {
        viewModel.loginSideEffects
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.LoginSuccess -> {
                        navigateToOnboarding()
                    }

                    is LoginSideEffect.LoginError -> {
                        //LoginError 필요시 추가 동작
                    }
                }
            }
    }
    SystemBarColorChanger(
        view = LocalView.current,
        color = Color.Transparent,
        shouldRollBack = false
    )

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true,
            transformColorForLightContent = { Gray900 }
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true,
            navigationBarContrastEnforced = false
        )
    }

    LoginScreen(
        onLoginClick = { viewModel.startKakaoLogin() }
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
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Spacer(modifier = Modifier.height(77.dp))
            Text(
                text = stringResource(R.string.done_worry),
                color = White,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier
                    .padding(start = 22.dp, top = 54.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(41.dp))
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
