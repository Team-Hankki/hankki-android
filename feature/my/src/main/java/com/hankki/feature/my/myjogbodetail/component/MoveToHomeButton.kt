package com.hankki.feature.my.myjogbodetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun MoveToHomeButton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .wrapContentSize()
            .background(Gray100)
            .padding(vertical = 10.dp)
            .padding(start = 12.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_add),
            contentDescription = "add",
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.go_to_store),
            color = Gray500,
            style = HankkiTheme.typography.body3,
        )
    }
}

@Composable
@Preview
fun MoveToHomeButtonPreview() {
    HankkijogboTheme {
        MoveToHomeButton()
    }
}

