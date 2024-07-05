package com.hankki.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HankkiStoreJogboBottomSheet(
    jogboItems: PersistentList<JogboItemEntity>,
    modifier: Modifier = Modifier,
    addNewJogbo: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    ModalBottomSheet(
        modifier = modifier,
        containerColor = White,
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.my_store_jogbo),
            textAlign = TextAlign.Center,
            style = HankkiTheme.typography.body2,
        )

        AddNewJogboButton(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 18.dp),
            onClick = addNewJogbo
        )

        LazyColumn(
            modifier = Modifier
                .background(Gray50)
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.jogbo_list),
                    style = HankkiTheme.typography.caption1,
                    color = Gray500
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(jogboItems) { item ->
                JogboItem(
                    imageUrl = item.imageUrl,
                    title = item.title,
                    tags = item.tags
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun AddNewJogboButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_square_btn),
            contentDescription = "button",
            tint = Gray300
        )
        Spacer(modifier = Modifier.width(18.dp))
        Text(
            text = stringResource(id = R.string.add_new_jogbo),
            style = HankkiTheme.typography.body2,
            color = Gray800
        )
    }
}

@Composable
fun JogboItem(
    imageUrl: String,
    title: String,
    tags: PersistentList<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "image",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(56.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                style = HankkiTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(tags) {
                    Text(
                        text = it,
                        style = HankkiTheme.typography.caption1,
                        color = Gray400
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_plus_btn_empty),
            contentDescription = "more",
            tint = Gray400
        )
    }
}

data class JogboItemEntity(
    val imageUrl: String,
    val title: String,
    val tags: PersistentList<String>,
)

@Preview
@Composable
fun HankkiStoreJogboBottomSheetPreview() {
    HankkijogboTheme {
        HankkiStoreJogboBottomSheet(
            jogboItems = persistentListOf(
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "title",
                    tags = persistentListOf("tag1", "tag2", "tag3")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "title",
                    tags = persistentListOf("tag1", "tag2", "tag3")
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "title",
                    tags = persistentListOf("tag1", "tag2", "tag3")
                ),
            )
        )
    }
}
