package com.hankki.feature.main.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.main.R

@Composable
fun SplashScreen(
    navigateToHome: (Boolean) -> Unit = {},
    navigateToLogIn: () -> Unit = {},
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.showSplash()
    }

    LaunchedEffect(viewModel.sideEffects, lifecycleOwner) {
        viewModel.sideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SplashSideEffect.NavigateToHome -> navigateToHome(true)
                    is SplashSideEffect.NavigateLogin -> navigateToLogIn()
                }
            }
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(205f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_subtitle),
                contentDescription = "subtitle"
            )
            Spacer(modifier = Modifier.height(14.dp))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_title),
                contentDescription = "title"
            )
            Spacer(modifier = Modifier.height(42.dp))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_logo),
                contentDescription = "logo"
            )
        }
        Spacer(modifier = Modifier.weight(253f))
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
