package com.hankki.feature.login

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiBlackButton
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun OnboardingRoute(
    navigateToHome: () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        DisposableEffect(Unit) {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            onDispose {
                WindowCompat.setDecorFitsSystemWindows(window, true)
                window.statusBarColor = Color.White.toArgb()
                window.navigationBarColor = Color.White.toArgb()
            }
        }
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "onboarding1",
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }) + fadeIn(
                animationSpec = tween(
                    300
                )
            )
        },
    ) {
        composable("onboarding1") { OnboardingScreen1(navController) }
        composable("onboarding2") { OnboardingScreen2(navController) }
        composable("onboarding3") { OnboardingScreen3(navController) }
        composable("onboarding4") { OnboardingScreen4(navigateToHome) }
    }
}

@Composable
fun OnboardingScreen1(navController: NavHostController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step1)
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = stringResource(R.string.skip),
            color = Gray400,
            style = HankkiTheme.typography.body3,
            modifier = Modifier
                .padding(top = 25.dp, end = 22.dp)
                .align(Alignment.TopEnd)
                .noRippleClickable { navController.navigate("onboarding4") }
        )

        Text(
            stringResource(R.string.rice_burden),
            color = White,
            style = HankkiTheme.typography.h1,
            modifier = Modifier
                .padding(top = 66.dp, start = 22.dp)
                .align(Alignment.TopStart)
        )

        HankkiButton(
            text = "다음으로",
            textStyle = HankkiTheme.typography.sub3,
            onClick = { navController.navigate("onboarding2") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun OnboardingScreen2(navController: NavHostController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step2)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = stringResource(R.string.skip),
            color = Gray400,
            style = HankkiTheme.typography.body3,
            modifier = Modifier
                .padding(top = 25.dp, end = 22.dp)
                .align(Alignment.TopEnd)
                .clickable { navController.navigate("onboarding4") }
        )

        Text(
            stringResource(R.string.under_8000),
            color = Gray900,
            style = HankkiTheme.typography.h1,
            modifier = Modifier
                .padding(top = 66.dp, start = 22.dp)
                .align(Alignment.TopStart)
        )

        HankkiButton(
            text = "다음으로",
            textStyle = HankkiTheme.typography.sub3,
            onClick = { navController.navigate("onboarding3") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun OnboardingScreen3(navController: NavHostController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step3)
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = stringResource(R.string.skip),
            color = Gray400,
            style = HankkiTheme.typography.body3,
            modifier = Modifier
                .padding(top = 25.dp, end = 22.dp)
                .align(Alignment.TopEnd)
                .clickable { navController.navigate("onboarding4") }
        )

        Text(
            stringResource(R.string.report),
            color = Gray900,
            style = HankkiTheme.typography.h1,
            modifier = Modifier
                .padding(top = 66.dp, start = 22.dp)
                .align(Alignment.TopStart)
        )

        HankkiButton(
            text = "다음으로",
            textStyle = HankkiTheme.typography.sub3,
            onClick = { navController.navigate("onboarding4") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
fun OnboardingScreen4(
    navigateToHome: () -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step4)
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

        HankkiBlackButton(
            text = "시작하기",
            textStyle = HankkiTheme.typography.sub3,
            onClick = navigateToHome,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .navigationBarsPadding()
        )
    }
}
