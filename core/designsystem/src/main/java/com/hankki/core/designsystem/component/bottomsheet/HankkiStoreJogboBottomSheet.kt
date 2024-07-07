package com.hankki.core.designsystem.component.bottomsheet

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray200
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HankkiStoreJogboBottomSheet(
    jogboItems: PersistentList<JogboItemEntity>,
    modifier: Modifier = Modifier,
    addNewJogbo: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        containerColor = White,
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
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
                    tags = item.tags,
                    isReported = item.isReported,
                    onDismissRequest = {
                        scope.launch {
                            delay(300)
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest()
                            }
                        }
                    }
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
    isReported: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_plus_btn_empty)
    }

    var color by remember {
        mutableStateOf(if (isReported) Gray200 else Gray400)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = "image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(56.dp),
                contentScale = ContentScale.Crop
            )
            if (isReported) {
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(56.dp)
                        .background(Color(0xFFFFFFFF).copy(alpha = 0.53f))
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = HankkiTheme.typography.body2,
                color = if (isReported) Gray300 else Gray800,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(tags) {
                    Text(
                        text = it,
                        style = HankkiTheme.typography.caption1,
                        color = if (isReported) Gray200 else Gray400
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "more",
            tint = color,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable {
                    if (!isReported) {
                        icon = R.drawable.ic_check_btn
                        color = Color.Unspecified
                        onDismissRequest()
                    }
                }
        )
    }
}

data class JogboItemEntity(
    val imageUrl: String,
    val title: String,
    val tags: PersistentList<String>,
    val isReported: Boolean,
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
                    tags = persistentListOf("tag1", "tag2", "tag3"),
                    isReported = true
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "title",
                    tags = persistentListOf("tag1", "tag2", "tag3"),
                    isReported = false
                ),
                JogboItemEntity(
                    imageUrl = "https://picsum.photos/200/300",
                    title = "title",
                    tags = persistentListOf("tag1", "tag2", "tag3"),
                    isReported = true
                ),
            )
        )
    }
}

@Preview
@Composable
fun HankkiStoreJogboItemPreview() {
    HankkijogboTheme {
        JogboItem(
            imageUrl = "https://picsum.photos/200/300",
            title = "title",
            tags = persistentListOf("tag1", "tag2", "tag3"),
            isReported = false
        )
    }
}

