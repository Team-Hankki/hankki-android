package com.hankki.feature.my.component

import androidx.compose.foundation.Image
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
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun EmptyStoreView(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_empty),
                contentDescription = "empty",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Gray500,
                style = HankkiTheme.typography.body6
            )
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStoreViewPreview() {
    HankkijogboTheme {
        EmptyStoreView(
            text = "한끼네 한정식"
        )
    }
}
