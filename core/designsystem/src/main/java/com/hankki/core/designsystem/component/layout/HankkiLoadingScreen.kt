package com.hankki.core.designsystem.component.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun CircleLoadingScreen(modifier: Modifier = Modifier) {
    LoadingScreen(modifier = modifier) {
        CircularProgressIndicator()
    }
}

@Composable
fun HankkiLoadingScreen(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )

    LoadingScreen(modifier = modifier) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun CircleLoadingScreenPreview() {
    HankkijogboTheme {
        CircleLoadingScreen()
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    HankkijogboTheme {
        HankkiLoadingScreen()
    }
}
