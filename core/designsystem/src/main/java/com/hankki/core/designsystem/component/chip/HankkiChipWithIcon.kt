package com.hankki.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.core.designsystem.theme.Yellow500
import com.hankki.core.designsystem.theme.Yellow300

@Composable
fun HankkiChipWithIcon(
    iconUrl: String,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .bounceClick(
                scaleDown = 0.88f,
                radius = 100f,
                onClick = onClick
            )
            .clip(RoundedCornerShape(100.dp))
            .border(1.dp, if (isSelected) Yellow500 else Gray200, RoundedCornerShape(100.dp))
            .background(if (isSelected) Yellow300 else White)
            .padding(vertical = 4.dp)
            .padding(start = 8.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = "icon",
            modifier = Modifier.size(28.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = title,
            style = if (isSelected) HankkiTheme.typography.body5 else HankkiTheme.typography.body6,
            color = if (isSelected) Gray700 else Gray400
        )
    }
}

@Preview
@Composable
fun HankkiChipWithIconPreview() {
    HankkijogboTheme {
        Column {
            HankkiChipWithIcon(
                iconUrl = "https://picsum.photos/200/300",
                title = "한식",
                isSelected = false
            )
            Spacer(modifier = Modifier.height(10.dp))
            HankkiChipWithIcon(
                iconUrl = "https://picsum.photos/200/300",
                title = "한식",
                isSelected = true
            )
        }
    }
}
