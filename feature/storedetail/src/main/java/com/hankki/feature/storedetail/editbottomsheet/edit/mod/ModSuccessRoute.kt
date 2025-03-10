package com.hankki.feature.storedetail.editbottomsheet.edit.mod

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.R
import com.hankki.feature.storedetail.StoreDetailViewModel

@Composable
fun ModSuccessRoute(
    onNavigateToEditMenu: () -> Unit,
    onNavigateToStoreDetailRoute: () -> Unit,
) {
    val viewModel: StoreDetailViewModel = hiltViewModel()
    val storeState by viewModel.storeState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchNickname()
    }

    ModSuccessScreen(
        nickname = storeState.nickname,
        onNavigateToEditMenu = onNavigateToEditMenu,
        onNavigateToStoreDetailRoute = onNavigateToStoreDetailRoute,
    )
}

@Composable
fun ModSuccessScreen(
    nickname: String,
    onNavigateToEditMenu: () -> Unit,
    onNavigateToStoreDetailRoute: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        HankkiTopBar()

        Spacer(modifier = Modifier.height(18.dp))
        Box {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_edit_completed),
                contentDescription = "Success",
                modifier = Modifier
                    .fillMaxSize()
            )

            Text(
                text = "${nickname}님이 말씀해주신대로\n메뉴 정보를 수정했어요!",
                style = HankkiTheme.typography.suitH2,
                color = Gray850,
                modifier = Modifier.padding(start = 22.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                HankkiMediumButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "다른 메뉴도 편집하기",
                    onClick = {
                        onNavigateToEditMenu()
                    },
                    enabled = true,
                    backgroundColor = White,
                    textStyle = HankkiTheme.typography.sub3,
                    textColor = Red500,
                    borderColor = Red500
                )
                Spacer(modifier = Modifier.height(8.dp))
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