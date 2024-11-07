package com.hankki.feature.storedetail.editbottomsheet.add.addsuccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.R

@Composable
fun AddMenuSuccessRoute(
    submittedMenuCount: Int,
    viewModel: AddMenuSuccessViewModel = hiltViewModel(),
    onNavigateToAddMenu: () -> Unit,
    onNavigateToStoreDetailRoute: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setSubmittedMenuCount(submittedMenuCount)
    }


    AddMenuSuccessScreen(
        nickname = state.nickname,
        submittedMenuCount = state.submittedMenuCount,
        onNavigateToAddMenu = onNavigateToAddMenu,
        onNavigateToStoreDetailRoute = onNavigateToStoreDetailRoute,
    )
}


@Composable
private fun AddMenuSuccessScreen(
    nickname: String,
    submittedMenuCount: Int,
    onNavigateToAddMenu: () -> Unit,
    onNavigateToStoreDetailRoute: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .offset(x = 6.dp, y = 2.dp)
                        .noRippleClickable(onClick = onNavigateToStoreDetailRoute),
                    tint = Gray700
                )
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box {
            Image(
                painter = painterResource(id = R.drawable.img_add_completed),
                contentDescription = "add_completed",
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "${nickname}님이 말씀해주신\n메뉴 ${submittedMenuCount}개를 새로 추가했어요!",
                style = HankkiTheme.typography.suitH2,
                color = Gray850,
                modifier = Modifier
                    .padding(start = 22.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                HankkiMediumButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "다른 메뉴도 추가하기",
                    onClick = onNavigateToAddMenu,
                    enabled = true,
                    backgroundColor = White,
                    textStyle = HankkiTheme.typography.sub3,
                    textColor = Red500,
                    borderColor = Red500
                )
                Spacer(modifier = Modifier.height(16.dp))
                HankkiMediumButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "완료",
                    onClick = onNavigateToStoreDetailRoute,
                    enabled = true,
                    textStyle = HankkiTheme.typography.sub3
                )
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 15.dp)
                )
            }
        }
    }
}