package com.hankki.core.designsystem.component.layout

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.hankki.core.designsystem.R

@Composable
fun BottomBlurLayout(
    modifier: Modifier = Modifier,
    @DrawableRes imageBlur: Int = R.drawable.img_white_gradient_bottom_short,
) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = imageBlur),
        contentDescription = "blur",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun TopBlurLayout(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.img_white_gradient_top),
        contentDescription = "blur",
        contentScale = ContentScale.FillWidth
    )
}

