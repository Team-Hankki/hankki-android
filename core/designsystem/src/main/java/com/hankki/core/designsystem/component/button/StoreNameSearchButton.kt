package com.hankki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.bounceClick
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.RedLight

@Composable
fun StoreNameSearchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .bounceClick(
                scaleDown = 0.94f,
                onClick = onClick
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Gray100)
            .padding(vertical = 12.dp)
            .padding(start = 10.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search icon",
            tint = Gray300
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(id = R.string.search_to_name),
            style = HankkiTheme.typography.body6,
            color = Gray400
        )
    }
}

@Composable
fun StoreNameSelectedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .bounceClick(onClick = onClick)
            .clip(RoundedCornerShape(10.dp))
            .background(RedLight)
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = HankkiTheme.typography.sub3,
            color = Red,
            modifier = Modifier.weight(1f, fill = false),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = "arrow down icon",
            tint = Gray400
        )
    }
}


@Preview
@Composable
fun StoreNameSearchButtonPreview() {
    HankkijogboTheme {
        Column {
            StoreNameSearchButton()
            StoreNameSelectedButton(
                text = "고동밥집 1호점에서 먹는 맛있는 음식이라고 하네요",
                modifier = Modifier.width(120.dp)
            )
            StoreNameSelectedButton(
                text = "고동밥",
            )
        }
    }
}
