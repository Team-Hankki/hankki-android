package com.hankki.core.designsystem.component.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.hankki.core.designsystem.R

@Composable
fun BottomBlurLayout(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.white_gradiend_bottom),
        contentDescription = "blur"
    )
}

@Composable
fun TopBlurLayout(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.white_gradient_top),
        contentDescription = "blur"
    )
}

