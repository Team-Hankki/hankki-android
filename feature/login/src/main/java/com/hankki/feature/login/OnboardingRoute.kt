package com.hankki.feature.login

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiOnboardingBlackButton
import com.hankki.core.designsystem.component.button.HankkiOnboardingButton
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun OnboardingRoute(
    navigateToUniversity: () -> Unit,
) {
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
        composable("onboarding4") { OnboardingScreen4(navigateToUniversity) }
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = stringResource(R.string.skip),
                color = Gray100,
                style = HankkiTheme.typography.body4,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 25.dp)
                    .statusBarsPadding()
                    .noRippleClickable {
                        navController.navigate("onboarding4") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                stringResource(R.string.rice_burden),
                color = White,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f))
            HankkiOnboardingButton(
                text = "다음으로",
                textStyle = HankkiTheme.typography.sub3,
                onClick = {
                    navController.navigate("onboarding2") {
                        popUpTo("onboarding1") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 22.dp)
                    .navigationBarsPadding()
            )
        }
    }
}


@Composable
fun OnboardingScreen2(navController: NavHostController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step2)
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = stringResource(R.string.skip),
                color = Gray400,
                style = HankkiTheme.typography.body4,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 25.dp)
                    .statusBarsPadding()
                    .noRippleClickable {
                        navController.navigate("onboarding4") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(23.dp))
            Text(
                text = stringResource(R.string.under_8000),
                color = Gray900,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f))
            HankkiOnboardingButton(
                text = "다음으로",
                textStyle = HankkiTheme.typography.sub3,
                onClick = {
                    navController.navigate("onboarding3") {
                        popUpTo("onboarding2") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 22.dp)
                    .navigationBarsPadding()
            )
        }
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
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = stringResource(R.string.skip),
                color = Gray400,
                style = HankkiTheme.typography.body4,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 25.dp)
                    .statusBarsPadding()
                    .noRippleClickable {
                        navController.navigate("onboarding4") {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(R.string.report),
                color = Gray900,
                style = HankkiTheme.typography.suitH1,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f))
            HankkiOnboardingButton(
                text = "다음으로",
                textStyle = HankkiTheme.typography.sub3,
                onClick = {
                    navController.navigate("onboarding4") {
                        popUpTo("onboarding3") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 22.dp)
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
fun OnboardingScreen4(
    navigateToUniversity: () -> Unit,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.step4)
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = 1,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        HankkiOnboardingBlackButton(
            text = "시작하기",
            textStyle = HankkiTheme.typography.sub3,
            onClick = navigateToUniversity,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 22.dp)
                .navigationBarsPadding()
        )
    }
}
