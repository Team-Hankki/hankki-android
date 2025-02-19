package com.hankki.feature.my.myjogbodetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.feature.my.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JogboFolder(
    title: String,
    chips: PersistentList<String>,
    userName: String,
    shareJogboDialogState: () -> Unit,
    isJogboOwner: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Red500)
            .padding(horizontal = 22.dp)
            .padding(top = 16.dp, bottom = 23.dp)
            .paint(
                painterResource(id = R.drawable.img_folder),
                contentScale = ContentScale.FillWidth
            )
    ) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_my_store),
                contentDescription = "store image",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(start = 17.dp)
                    .size(45.dp),
            )

            Text(
                text = title,
                color = Gray900,
                style = HankkiTheme.typography.h2,
                modifier = Modifier
                    .padding(start = 22.dp, end = 50.dp)
                    .padding(top = 11.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            LazyRow(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(chips) { chip ->
                    JogboHashtagChip(chiptext = chip)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(start = 21.dp, end = 17.dp)
                    .padding(bottom = 13.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.img_user_hand),
                        contentDescription = "user image",
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = userName,
                        style = HankkiTheme.typography.body6,
                        color = Gray600,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                JogboShareButton(
                    modifier = Modifier.alpha(if (isJogboOwner) 1f else 0f),
                    showShareDialog = {
                        if (isJogboOwner) shareJogboDialogState() else {
                        }
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun JogboFolderPreview() {
    HankkijogboTheme {
        Column {
            JogboFolder(
                title = "족보 이름",
                chips = persistentListOf("태그1", "태그2"),
                userName = "사용자 이름",
                shareJogboDialogState = {},
                isJogboOwner = false
            )

            JogboFolder(
                title = "족보 이름",
                chips = persistentListOf("태그1", "태그2"),
                userName = "사용자 이름",
                shareJogboDialogState = {},
                isJogboOwner = true
            )
        }
    }
}
