package com.hankki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.dashedBorder
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme

@Composable
fun AddPhotoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Gray100)
            .dashedBorder(
                strokeWidth = 2.dp,
                color = Gray200,
                cornerRadiusDp = 10.dp
            )
            .padding(14.dp)
            .noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_add_photo_alternate),
            contentDescription = "photo icon",
            tint = Gray500
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "대표 음식 이미지 첨부하기",
            style = HankkiTheme.typography.body3,
            color = Gray500
        )
    }
}

@Preview
@Composable
fun AddPhotoButtonPreview() {
    HankkijogboTheme {
        AddPhotoButton()
    }
}
