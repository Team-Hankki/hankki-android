package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.feature.my.R
import kotlinx.collections.immutable.PersistentList

@Composable
fun JogboFolder(
    title: String,
    chips: PersistentList<String>,
    userNickname: String,
    shareJogbo: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red)
            .padding(top = 4.dp, bottom = 22.dp)
            .padding(horizontal = 22.dp)
            .paint(
                painterResource(id = R.drawable.ic_my_jogbo_fold),
                contentScale = ContentScale.FillWidth
            )
    ) {
        Column {
            Icon(
                painter = painterResource(id = R.drawable.ic_my_store),
                contentDescription = "store image",
                modifier = Modifier
                    .padding(top = 16.dp, start = 18.dp)
                    .size(43.dp),
                tint = Color.Unspecified
            )

            Text(
                text = title,
                color = Gray900,
                style = HankkiTheme.typography.h2,
                modifier = Modifier.padding(start = 22.dp, end = 50.dp, top = 11.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            LazyRow(
                modifier = Modifier.padding(start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(chips) { chip ->
                    JogboHashtagChip(chiptext = chip)
                }
            }

            Spacer(modifier = Modifier.height(38.dp))

            Row(
                modifier = Modifier
                    .padding(start = 21.dp, end = 17.dp, bottom = 13.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user_image),
                        contentDescription = "user image",
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = userNickname,
                        style = HankkiTheme.typography.body4,
                        color = Gray600,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                JogboShareButton(showShareDialog = shareJogbo)
            }
        }

    }
}
