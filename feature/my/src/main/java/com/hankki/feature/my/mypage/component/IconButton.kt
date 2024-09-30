package com.hankki.feature.my.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun IconButton(
    image: Int,
    text: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.noRippleClickable(onClick = onclick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(image),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = HankkiTheme.typography.suitBody3,
            color = Gray600,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun IconButtonPreview() {
    HankkijogboTheme {
        IconButton(
            image = R.drawable.img_user_profile,
            text = "나의 족보",
            onclick = {},
        )
    }
}
