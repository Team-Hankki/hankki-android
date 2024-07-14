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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.StoreDetailButton
import com.hankki.core.designsystem.component.layout.StoreDetailMenuBox
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900

@Composable
fun StoreDetailRoute() {
    StoreDetailScreen()
}

@Composable
fun StoreDetailScreen() {
    val isLiked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                imageVector = ImageVector.vectorResource(id = com.hankki.feature.storedetail.R.drawable.img_default_store_detail),
                contentDescription = "식당 사진",
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.img_black_gradient_top),
                contentDescription = "black gradient",
                modifier = Modifier.fillMaxWidth()
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
                title = "한끼네 한정식",
                tag = "한식",
                menuItems = listOf(
                    "수육 정식" to "7,900원",
                    "제육 정식" to "7,900원",
                    "꼬막 정식" to "7,900원"
                ),
                leadingButton1 = {
                    StoreDetailButton(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = if (isLiked.value) R.drawable.ic_red_like else R.drawable.ic_like),
                                contentDescription = "좋아요 아이콘",
                                tint = Color.Unspecified
                            )
                        },
                        content = {
                            Text(
                                text = "299",
                                style = HankkiTheme.typography.sub3
                            )
                        },
                        onClick = {
                            isLiked.value = !isLiked.value
                            /* Handle leading button click */
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
                                text = "나의 족보에 추가",
                                style = HankkiTheme.typography.sub3
                            )
                        },
                        onClick = { /* Handle trailing button click */ }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "내가 알고 있는 정보와 다른가요?",
                style = HankkiTheme.typography.sub1,
                color = Gray900
            )

            Spacer(modifier = Modifier.height(16.dp))

            val selectedIndex = remember { mutableIntStateOf(-1) }
            val buttonLabels = listOf(
                "식당이 사라졌어요",
                "더이상 8,000원 이하인 메뉴가 없어요",
                "부적절한 제보에요"
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

            Button(
                onClick = { /* TODO: handle submit */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFF6F61)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "제보하기",
                    color = Color.White,
                    style = HankkiTheme.typography.body4
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantMenuScreen() {
    HankkijogboTheme {
        StoreDetailScreen()
    }
}
