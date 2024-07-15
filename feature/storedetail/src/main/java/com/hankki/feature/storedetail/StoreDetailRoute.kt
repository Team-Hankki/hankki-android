package com.hankki.feature.storedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreDetailButton
import com.hankki.core.designsystem.component.layout.StoreDetailMenuBox
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.feature.storedetail.model.MenuItem
import com.hankki.feature.storedetail.model.StoreDetail

@Composable
fun StoreDetailRoute() {
    val viewModel: StoreDetailViewModel = hiltViewModel()
    val storeDetail by viewModel.storeDetail.collectAsStateWithLifecycle()

    storeDetail?.let {
        StoreDetailScreen(
            storeDetail = it,
            onLikeClicked = { viewModel.toggleLike() }
        )
    }
}

@Composable
fun StoreDetailScreen(storeDetail: StoreDetail, onLikeClicked: () -> Unit) {
    var isLiked by remember { mutableStateOf(storeDetail.isLiked) }
    var heartCount by remember { mutableIntStateOf(storeDetail.heartCount) }

    LaunchedEffect(storeDetail) {
        isLiked = storeDetail.isLiked
        heartCount = storeDetail.heartCount
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                imageVector = ImageVector.vectorResource(id = com.hankki.feature.storedetail.R.drawable.img_default_store_detail),
                contentDescription = "식당 사진",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.img_black_gradient_top),
                contentDescription = "black gradient",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Column {
                Spacer(modifier = Modifier.statusBarsPadding())
                HankkiTopBar(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "뒤로가기",
                            modifier = Modifier
                                .size(48.dp)
                                .noRippleClickable { /* TODO: navigate back */ },
                            tint = Color.Unspecified
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .offset(y = (-75).dp)
                .padding(18.dp)
                .fillMaxSize()
        ) {
            StoreDetailMenuBox(
                title = storeDetail.name,
                tag = storeDetail.category,
                menuItems = storeDetail.menus.map { it.name to "${it.price}원" },
                leadingButton1 = {
                    StoreDetailButton(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = if (isLiked) R.drawable.ic_red_like else R.drawable.ic_like),
                                contentDescription = "좋아요 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = heartCount.toString(),
                                style = HankkiTheme.typography.sub3
                            )
                        },
                        onClick = {
                            onLikeClicked()
                        }
                    )
                },
                leadingButton2 = {
                    StoreDetailButton(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_circle_dark_plus),
                                contentDescription = "추가 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = stringResource(id = R.string.add_new_jogbo),
                                style = HankkiTheme.typography.sub3
                            )
                        },
                        onClick = { /*TODO: navigate add jogbo */ }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = com.hankki.feature.storedetail.R.string.is_it_different),
                style = HankkiTheme.typography.sub1,
                color = Gray900
            )

            Spacer(modifier = Modifier.height(16.dp))

            val selectedIndex = remember { mutableIntStateOf(-1) }
            val buttonLabels = listOf(
                stringResource(id = com.hankki.feature.storedetail.R.string.missing_store),
                stringResource(id = com.hankki.feature.storedetail.R.string.no_longer_8000),
                stringResource(id = com.hankki.feature.storedetail.R.string.inappropriate_report)
            )

            buttonLabels.forEachIndexed { index, label ->
                val isSelected = selectedIndex.intValue == index
                StoreDetailButton(
                    content = {
                        Text(
                            text = label,
                            style = HankkiTheme.typography.body1.copy(color = if (isSelected) Color.Red else Color.Black),
                            modifier = Modifier.weight(1f)
                        )
                    },
                    onClick = {
                        selectedIndex.intValue = index
                    },
                    tailingIcon = {
                        Icon(
                            painter = painterResource(id = if (isSelected) R.drawable.ic_check_btn else R.drawable.ic_uncheck_btn),
                            contentDescription = "체크박스 아이콘",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    },
                    isSelected = isSelected
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            HankkiButton(
                text = "제보하기",
                onClick = { /* TODO: handle submit */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 92.dp),
                textStyle = HankkiTheme.typography.body4
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantMenuScreen() {
    HankkijogboTheme {
        StoreDetailScreen(
            storeDetail = StoreDetail(
                name = "한끼네 한정식",
                category = "한식",
                isLiked = false,
                heartCount = 299,
                imageUrls = listOf(),
                menus = listOf(
                    MenuItem(name = "수육정식", price = 7900),
                    MenuItem(name = "제육정식", price = 8900),
                    MenuItem(name = "꼬막정식", price = 7900)
                )
            ),
            onLikeClicked = {}
        )
    }
}
