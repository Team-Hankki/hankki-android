package com.hankki.feature.my.myjogbodetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun JogboShareButton(
    modifier: Modifier = Modifier,
    showShareDialog: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(88.dp))
            .background(Gray800)
            .padding(top = 7.dp, bottom = 9.dp, start = 9.dp, end = 16.dp)
            .noRippleClickable(showShareDialog),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_share),
            contentDescription = "share"
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(text = "공유", style = HankkiTheme.typography.body5, color = White)
    }
}

@Composable
@Preview
fun JogboShareButtonPreview() {
    HankkijogboTheme {
        JogboShareButton(showShareDialog = {})
    }
}
