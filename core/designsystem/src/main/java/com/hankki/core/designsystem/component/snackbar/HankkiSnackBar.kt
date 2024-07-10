package com.hankki.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray900

@Composable
fun HankkiSnackBar(content: @Composable () -> Unit) {
    val colorWithTransparency = Gray900.copy(alpha = 0.85f)
    Box(
        modifier = Modifier
            .background(color = colorWithTransparency, shape = RoundedCornerShape(6.dp))
            .padding(vertical = 14.dp, horizontal = 22.dp)
    ) {
        content()
    }
}