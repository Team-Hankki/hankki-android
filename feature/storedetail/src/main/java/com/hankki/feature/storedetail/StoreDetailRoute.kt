package com.hankki.feature.storedetail

import StoreDetailViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreDetailButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.storedetail.component.StoreDetailMenuBox
import com.hankki.feature.storedetail.model.MenuItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun StoreDetailRoute() {
    val viewModel: StoreDetailViewModel = hiltViewModel()
    val storeState by viewModel.storeState.collectAsStateWithLifecycle()

    when (val state = storeState.storeDetail) {
        is UiState.Loading -> {}

        is UiState.Success -> {
            with(state.data) {
                StoreDetailScreen(
                    title = name,
                    tag = category,
                    menuItems = menus.toPersistentList(),
                    isLiked = storeState.isLiked,
                    heartCount = storeState.heartCount,
                    selectedIndex = storeState.selectedIndex,
                    buttonLabels = storeState.buttonLabels,
                    onLikeClicked = { viewModel.toggleLike() },
                    onSelectIndex = { index -> viewModel.updateSelectedIndex(index) }
                )
            }
        }

        is UiState.Failure -> {}
    }
}

@Composable
fun StoreDetailScreen(
    title: String,
    tag: String,
    menuItems: PersistentList<MenuItem>,
    isLiked: Boolean,
    heartCount: Int,
    selectedIndex: Int,
    buttonLabels: PersistentList<String>,
    onLikeClicked: () -> Unit,
    onSelectIndex: (Int) -> Unit
) {
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
                .offset(y = (-50).dp)
                .padding(horizontal = 18.dp)
                .fillMaxSize()
        ) {
            StoreDetailMenuBox(
                title = title,
                tag = tag,
                menuItems = menuItems,
                likeButton = {
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
                addMyJogboButton = {
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

            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = com.hankki.feature.storedetail.R.string.is_it_different),
                    style = HankkiTheme.typography.sub1,
                    color = Gray900,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))
                buttonLabels.forEachIndexed { index, label ->
                    val isSelected = selectedIndex == index
                    StoreDetailButton(
                        content = {
                            Text(
                                text = label,
                                style = HankkiTheme.typography.body3.copy(color = if (isSelected) Color.Red else Gray400),
                                modifier = Modifier.weight(1f),
                            )
                        },
                        onClick = {
                            onSelectIndex(index)
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
                        .fillMaxWidth(0.4f),
                    textStyle = HankkiTheme.typography.sub3,
                    enabled = selectedIndex != -1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantMenuScreen() {
    HankkijogboTheme {
        StoreDetailScreen(
            title = "한끼네 한정식",
            tag = "한식",
            menuItems = listOf(
                MenuItem(name = "수육정식", price = 7900),
                MenuItem(name = "제육정식", price = 8900),
                MenuItem(name = "꼬막정식", price = 7900)
            ).toPersistentList(),
            isLiked = false,
            heartCount = 299,
            selectedIndex = -1,
            buttonLabels = listOf(
                "가게 누락",
                "더 이상 8000원이 아닙니다",
                "부적절한 신고"
            ).toPersistentList(),
            onLikeClicked = {},
            onSelectIndex = {}
        )
    }
}
