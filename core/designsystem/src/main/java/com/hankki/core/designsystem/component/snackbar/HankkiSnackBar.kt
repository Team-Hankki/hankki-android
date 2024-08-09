package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900

@Composable
fun HankkiSnackBar(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color = Gray800)
            .padding(vertical = 14.dp, horizontal = 22.dp)
    ) {
        content()
    }
}
