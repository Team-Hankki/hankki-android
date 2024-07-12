package com.hankki.core.designsystem.component.topappbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun HankkiTopBar(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ){
            leadingIcon()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            content()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ){
            trailingIcon()
        }
    }
}

@Preview
@Composable
fun HankkiTopBarPreview() {
    HankkiTopBar(
        leadingIcon = {},
        content = {
            Text(
                text = "hi",
                style = HankkiTheme.typography.suitH2,
                color = Gray900
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown_btn),
                contentDescription = "button",
                modifier = Modifier.noRippleClickable(onClick = {}),
                tint = Gray300
            )
        },
        trailingIcon = {}
    )
}

