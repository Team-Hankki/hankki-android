package com.hankki.feature.storedetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun StoreDetailHeadBox(
    title: String,
    categoryImageUrl: String,
    tag: String,
    likeButton: @Composable () -> Unit,
    addMyJogboButton: @Composable () -> Unit,
    latitude: Double,
    longitude: Double,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = categoryImageUrl,
                contentDescription = "카테고리 이미지",
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(
                text = tag,
                style = HankkiTheme.typography.caption4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Gray900,
            )
        }

        Text(
            text = title,
            modifier = Modifier.padding(start = 22.dp),
            style = HankkiTheme.typography.h3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Gray900,
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            likeButton()
            Spacer(modifier = Modifier.width(13.dp))
            addMyJogboButton()
        }

        Spacer(modifier = Modifier.padding(top = 12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp)
                .height(1.dp)
                .background(Gray100)
        )

        Spacer(modifier = Modifier.padding(top = 18.dp))
        StoreDetailMapBox(latitude = latitude, longitude = longitude)
        Spacer(modifier = Modifier.padding(bottom = 22.dp))
    }
}
