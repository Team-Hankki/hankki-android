package com.hankki.feature.my.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.chip.HankkiCategoryChip
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreItem(
    imageUrl: String,
    category: String,
    name: String,
    price: Int,
    heartCount: Int,
    isIconUsed: Boolean,
    isIconSelected: Boolean,
    modifier: Modifier = Modifier,
    editSelected: () -> Unit = {},
    onClickItem: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .padding(vertical = 16.dp)
            .noRippleClickable(onClickItem),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Store Image",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.wrapContentHeight().weight(1f)) {
            Row(modifier = Modifier.padding(top = 11.5.dp)) {
                Text(
                    text = name,
                    style = HankkiTheme.typography.suitSub1,
                    color = Gray900,
                    maxLines = 1,
                    overflow = Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(5.dp))

                HankkiCategoryChip(text = category)
            }

            Spacer(modifier = Modifier.height(7.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_food),
                    contentDescription = "icon",
                    tint = Gray300
                )

                Spacer(modifier = Modifier.width(3.dp))

                Text(
                    text = "${price}원",
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
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "icon",
                    tint = Gray300
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "$heartCount",
                    style = HankkiTheme.typography.button1,
                    color = Gray500
                )
            }
        }

        if (isIconUsed) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_filled),
                contentDescription = "plus button",
                modifier = Modifier
                    .size(52.dp)
                    .noRippleClickable(onClick = editSelected),
                tint = if (isIconSelected) Red else Gray200
            )
        }
    }
}

@Preview
@Composable
fun StoreItemPreview() {
    HankkijogboTheme {
        StoreItem(
            imageUrl = "",
            category = "한식",
            name = "한끼네 한정식",
            price = 7900,
            heartCount = 300,
            isIconUsed = true,
            isIconSelected = true,
            editSelected = {}
        )
    }
}
