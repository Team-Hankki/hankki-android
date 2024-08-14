package com.hankki.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.formatPrice
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.chip.HankkiCategoryChip
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreItem(
    storeId: Long,
    storeImageUrl: String,
    category: String,
    storeName: String,
    price: Int,
    heartCount: Int,
    modifier: Modifier = Modifier,
    onClickItem: (Long) -> Unit = {},
    onPlusClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .padding(horizontal = 22.dp, vertical = 16.dp)
            .noRippleClickable { onClickItem(storeId) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = storeImageUrl,
            contentDescription = "Store Image",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.wrapContentHeight()) {
            HankkiCategoryChip(text = category)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = storeName,
                style = HankkiTheme.typography.suitSub1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_food),
                    contentDescription = "icon",
                    tint = Gray300,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${price.toString().formatPrice()}원",
                    style = HankkiTheme.typography.button1,
                    color = Gray500
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_ellipse),
                    contentDescription = "icon",
                    tint = Gray300
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = "icon",
                    tint = Gray300,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "$heartCount",
                    style = HankkiTheme.typography.button1,
                    color = Gray500
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_plus_btn_filled),
            contentDescription = "plus button",
            modifier = Modifier.noRippleClickable(onClick = onPlusClick),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun StoreItemPreview() {
    HankkijogboTheme {
        StoreItem(
            storeId = 1,
            storeImageUrl = "https://github.com/Team-Hankki/hankki-android/assets/52882799/e9b059f3-f283-487c-ae92-29eb160ccb14",
            category = "한식",
            storeName = "한끼네 한정식",
            price = 7900,
            heartCount = 300
        )
    }
}
