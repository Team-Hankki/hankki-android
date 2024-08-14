package com.hankki.core.designsystem.component.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
private fun BaseEmptyView(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EmptyImageWithText(text = text)
            content()
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
fun EmptyImageWithText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_empty),
            contentDescription = "empty",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Gray500,
            style = HankkiTheme.typography.body2
        )
    }
}

@Composable
fun EmptyView(
    text: String,
    modifier: Modifier = Modifier,
) {
    BaseEmptyView(
        text = text,
        modifier = modifier
    )
}

@Composable
fun EmptyViewWithButton(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BaseEmptyView(
        text = text,
        modifier = modifier,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun BaseEmptyViewPreview() {
    HankkijogboTheme {
        BaseEmptyView(
            text = "한끼네 한정식"
        )
    }
}
