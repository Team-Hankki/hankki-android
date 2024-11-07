package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red400
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.R

@Composable
fun SegmentedButton(
    modifier: Modifier = Modifier,
    option1: String,
    option2: String,
    enabled: Boolean = true,
    onOptionSelected: (String) -> Unit,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (enabled) Red500 else Red400),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (enabled) {
                            Modifier.noRippleClickable {
                                onOptionSelected(option1)
                            }
                        } else {
                            Modifier
                        }
                    )
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option1,
                    fontSize = 16.sp,
                    color = White,
                    style = HankkiTheme.typography.body2
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 15.dp)
                    .background(Color(0x1A9B331C))
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (enabled) {
                            Modifier.noRippleClickable {
                                onOptionSelected(option2)
                            }
                        } else {
                            Modifier
                        }
                    )
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = option2,
                    fontSize = 16.sp,
                    color = White,
                    style = HankkiTheme.typography.body2
                )
            }
        }
    }
}