package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun ButtonWithArrowIcon(
    itemTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemTitle,
            style = HankkiTheme.typography.body3,
            color = Gray900
        )
        Image(painterResource(id = R.drawable.ic_arrow_right), "ic_arrow_right")
    }
    HorizontalDivider(thickness = 1.dp, color = Gray200)
}

@Composable
@Preview
fun ItemWithArrowPreview() {
    HankkijogboTheme {
        ButtonWithArrowIcon(itemTitle = "title", onClick = {}, modifier = Modifier)
    }
}
