package com.hankki.core.designsystem.component.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.formatPrice
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StoreItem(
    storeId: Long,
    storeImageUrl: String?,
    category: String,
    storeName: String,
    price: Int,
    heartCount: Int,
    modifier: Modifier = Modifier,
    onClickItem: (Long) -> Unit = {},
    onLongClickItem: ((Long) -> Unit)? = null,
    iconButton: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .padding(horizontal = 22.dp, vertical = 16.dp)
            .then(
                if (onLongClickItem != null) Modifier.combinedClickable(
                    onClick = { onClickItem(storeId) },
                    onLongClick = { onLongClickItem(storeId) }
                )
                else Modifier.noRippleClickable { onClickItem(storeId) }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = storeImageUrl ?: R.drawable.img_store_default,
            contentDescription = "Store Image",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1f)
        ) {
            Text(
                text = category,
                style = HankkiTheme.typography.caption4,
                color = Gray700
            )
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = storeName,
                    style = HankkiTheme.typography.body5,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f, false)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_filled),
                    contentDescription = "heart icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = heartCount.toString(),
                    style = HankkiTheme.typography.caption4,
                    color = Gray700
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "최저",
                    style = HankkiTheme.typography.caption4,
                    color = Gray400
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${price.toString().formatPrice()}원",
                    style = HankkiTheme.typography.caption1,
                    color = Gray700
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))

        iconButton()
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
        ) {
            // Icon Button
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_filled),
                contentDescription = "plus button",
                modifier = Modifier.noRippleClickable(onClick = {}),
                tint = Color.Unspecified
            )
        }
    }
}
