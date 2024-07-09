package com.hankki.feature.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.AddPhotoButton
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.StoreNameSearchButton
import com.hankki.core.designsystem.component.chip.HankkiChipWithIcon
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.textfield.HankkiMenuTextField
import com.hankki.core.designsystem.component.textfield.HankkiPriceTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ReportRoute(
    navigateToLogin: () -> Unit,
    navigateToUniversity: () -> Unit,
) {
    ReportScreen()
}

@Composable
fun ReportScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable {
                            // 뒤로가기 설정
                        }
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
            ) {
                Spacer(modifier = Modifier.height(18.dp))

                ReportTopContent {
                    // TODO: Store 검색 화면 이동
                }

                Spacer(modifier = Modifier.height(26.dp))
                HorizontalDivider(
                    thickness = 12.dp,
                    color = Gray100
                )
                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 22.dp)
                        .fillMaxWidth()
                ) {
                    StoreCategoryChips() // list 아이템들 연결

                    Spacer(modifier = Modifier.height(50.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "식당 이름을 알려주세요",
                            style = HankkiTheme.typography.sub1,
                            color = Gray900
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "한끼로 적당한 메뉴를 추천해주세요",
                            style = HankkiTheme.typography.body4,
                            color = Gray400
                        )

                        Spacer(modifier = Modifier.height(32.dp))
                        AddPhotoButton(modifier = Modifier.fillMaxWidth()) {
                            // TODO: 사진 업로드
                        }

                        // TODO: 리스트로 변경
                        Spacer(modifier = Modifier.height(32.dp))
                        MenuWithPriceInputComponent()
                        Spacer(modifier = Modifier.height(12.dp))
                        MenuWithPriceInputComponent()
                        Spacer(modifier = Modifier.height(12.dp))
                        MenuWithPriceInputComponent()
                        Spacer(modifier = Modifier.height(12.dp))
                        MenuWithPriceInputComponent()
                        Spacer(modifier = Modifier.height(24.dp))

                        AddMenuButton {
                            // TODO: 메뉴 추가
                        }

                        Spacer(modifier = Modifier.height(35.dp))
                        BottomBlurLayout()
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                BottomBlurLayout()
                Column {
                    HankkiButton(
                        text = "제보하기",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        onClick = {
                            // TODO: 제보하기 api 연결
                        }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }

        }
    }
}

@Composable
fun ReportTopContent(
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "한끼족보의 52번째",
            style = HankkiTheme.typography.body4,
            color = Red,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            StoreNameSearchButton(onClick = onClick)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "을 제보할래요",
                style = HankkiTheme.typography.body6,
                color = Gray900
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StoreCategoryChips() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "식당 종류를 알려주세요",
            style = HankkiTheme.typography.sub1,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(14.dp))
        FlowRow(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val list = persistentListOf(
                "한식", "일식", "중식", "양식", "분식", "패스트푸드", "카페", "디저트", "기타"
            )

            list.forEach { item ->
                HankkiChipWithIcon(
                    iconUrl = "",
                    title = item,
                    isSelected = item == "일식"
                )
            }
        }
    }
}

@Composable
fun MenuWithPriceInputComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HankkiMenuTextField(
            value = "된장찌개",
            onTextChanged = {},
            isFocused = false,
        )
        Spacer(modifier = Modifier.width(8.dp))
        HankkiPriceTextField(
            value = "10000",
            onTextChanged = {},
            isFocused = false,
            isError = true,
        )
        Spacer(modifier = Modifier.width(3.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_circle_x),
            contentDescription = "delete",
            tint = Gray300,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun AddMenuButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier.noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_circle_dark_plus),
            contentDescription = "plus",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "메뉴 추가하기",
            style = HankkiTheme.typography.sub3,
            color = Gray400
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    HankkijogboTheme {
        ReportScreen()
    }
}
