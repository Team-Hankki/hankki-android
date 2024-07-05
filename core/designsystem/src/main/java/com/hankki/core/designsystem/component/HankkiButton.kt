package com.hankki.core.designsystem.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun HankkiButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .noRippleClickable { onClick() }
            .height(54.dp)
            .widthIn(min = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Red,
            disabledContainerColor = Red.copy(alpha = 0.38f)
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    ) {
        Text(text = text, color = White)
    }
}