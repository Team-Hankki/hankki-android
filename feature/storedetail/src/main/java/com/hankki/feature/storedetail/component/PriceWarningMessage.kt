package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.WarnRed
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.R

@Composable
fun PriceWarningMessage(
    onDeleteClick: () -> Unit,
    onDismissClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray200,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = Gray50,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(
                start = 24.dp,
                top = 16.dp,
                bottom = 16.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "가격이 올랐군요",
                style = HankkiTheme.typography.body8,
                color = Gray600
            )
            Text(
                text = "8천원을 넘는 메뉴는 삭제를 추천해요",
                style = HankkiTheme.typography.body5,
                color = Gray850
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .noRippleClickable(onClick = onDeleteClick),
                color = WarnRed,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "삭제하기",
                    style = HankkiTheme.typography.body3,
                    color = White,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    )
                )
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                contentDescription = "닫기",
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickable(onClick = onDismissClick),
                tint = Gray300
            )
        }
    }
}