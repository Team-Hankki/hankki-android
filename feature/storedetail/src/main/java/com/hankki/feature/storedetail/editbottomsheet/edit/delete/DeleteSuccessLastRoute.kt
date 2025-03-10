package com.hankki.feature.storedetail.editbottomsheet.edit.delete

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.designsystem.component.button.HankkiMediumButton
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.event.LocalSnackBarTrigger
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.storedetail.R

@Composable
fun DeleteSuccessLastRoute(
    viewModel: DeleteSuccessViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val snackBar = LocalSnackBarTrigger.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sideEffectFlow = viewModel.sideEffect

    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                DeleteSuccessSideEffect.NavigateToStoreDetail -> onNavigateToHome()
                is DeleteSuccessSideEffect.ShowError -> {
                    snackBar(sideEffect.message)
                }
                else -> {}
            }
        }
    }

    DeleteSuccessLastScreen(
        nickname = uiState.nickname,
        onNavigateToStoreDetailRoute = viewModel::navigateToStoreDetail
    )
}

@Composable
fun DeleteSuccessLastScreen(
    nickname: String,
    onNavigateToStoreDetailRoute: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        HankkiTopBar()

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "${nickname}님이 말씀해주신대로\n메뉴를 삭제했어요!",
            style = HankkiTheme.typography.suitH2,
            color = Gray850,
            modifier = Modifier.padding(start = 22.dp)
        )

        Box {
            Image(
                painter = painterResource(id = R.drawable.img_deleted_complete),
                contentDescription = "delete_complete",
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

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
