package com.hankki.feature.my

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.component.JogboHashtagChip
import com.hankki.feature.my.component.JogboShareButton
import com.hankki.feature.my.component.StoreItem

@Composable
fun MyJogboDetailRoute(paddingValues: PaddingValues) {
    MyJogboDetailScreen(paddingValues = paddingValues)
}

@Composable
fun MyJogboDetailScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyJogboTitle("성대생 점심 추천 맛집임 많관부")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            // List items
            items(5) {
                StoreItem(
                    storeImageUrl = "",
                    category = "한식",
                    storeName = "한끼네 한정식",
                    price = 7900,
                    heartCount = 300,
                    isIconUsed = false
                )
            }

            // Footer item
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .wrapContentSize()
                        .background(Gray100)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Gray500
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "식당 구경하러 가기",
                        color = Gray500,
                        style = HankkiTheme.typography.body6
                    )
                }
            }
        }
    }
}

@Composable
fun MyJogboTitle(jogboTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Red)
            .padding(top = 4.dp, bottom = 22.dp)
            .paint(
                painterResource(id = R.drawable.img_my_jogbo_detail),
                contentScale = ContentScale.Fit
            )
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_pizza),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp, start = 18.dp)
                    .size(43.dp)
            )
            Text(
                text = jogboTitle,
                color = Gray900,
                style = HankkiTheme.typography.h2,
                modifier = Modifier.padding(start = 22.dp, end = 50.dp, top = 11.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            ) //이부분 색깔미지정되어있음 물어보기
            LazyRow(
                modifier = Modifier.padding(start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    JogboHashtagChip("여긴꼭가봐")
                }
                item {
                    JogboHashtagChip(chiptext = "여긴꼭가봐")
                }
            }
            Spacer(modifier = Modifier.height(38.dp))
            Row(
                modifier = Modifier
                    .padding(start = 21.dp, end = 17.dp, bottom = 13.dp)
                    .fillMaxWidth(), //너비설정어케함?ㅜ
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_spoon),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    Text(
                        text = "김한끼",
                        style = HankkiTheme.typography.body4,
                        color = Gray600,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                JogboShareButton()
            }
        }

    }
}

@Preview
@Composable
fun MyJogboDetailScreenPreview() {
    HankkijogboTheme {
        MyJogboDetailScreen(PaddingValues())
    }
}
