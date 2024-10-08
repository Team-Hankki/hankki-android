package com.hankki.feature.storedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme

@Composable
fun EditModSucceedRoute(
    storeId: Long,
    viewModel: StoreDetailViewModel = hiltViewModel(),
    onNavigateToEditMenu:() -> Unit,
    onNavigateToStoreDetailRoute: () -> Unit
) {
    val storeState by viewModel.storeState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchNickname()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "${storeState.nickname}님이 말씀해주신대로 메뉴 정보를 수정했어요!",
            style = HankkiTheme.typography.suitH2,
            color = Gray900,
            modifier = Modifier.padding(start = 22.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Spacer(modifier = Modifier.weight(1f))
        HankkiMediumButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "다른 메뉴도 수정하기",
            onClick = {
                onNavigateToEditMenu()
            },
            enabled = true,
            textStyle = HankkiTheme.typography.sub3
        )
        Spacer(modifier = Modifier.height(16.dp))
        HankkiMediumButton(
            modifier = Modifier
                .fillMaxWidth(),
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
