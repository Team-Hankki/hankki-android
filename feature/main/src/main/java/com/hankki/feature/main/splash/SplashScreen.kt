package com.hankki.feature.main.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.main.R

@Composable
fun SplashScreen() {
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
