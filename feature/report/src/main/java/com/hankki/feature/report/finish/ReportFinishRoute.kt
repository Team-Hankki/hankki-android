package com.hankki.feature.report.finish

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.designsystem.component.bottomsheet.HankkiStoreJogboBottomSheet
import com.hankki.core.designsystem.component.bottomsheet.JogboResponseModel
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.HankkiTextButton
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.report.R
import com.hankki.feature.report.finish.component.ReportFinishCard
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ReportFinishRoute(
    storeName: String,
    storeId: Long,
    count: Long,
    navigateToHome: () -> Unit,
    navigateToStoreDetail: (storeId: Long) -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    viewModel: ReportFinishViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.setStoreInfo(
            count = count,
            storeName = storeName,
            storeId = storeId
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is ReportFinishSideEffect.NavigateToStoreDetail -> navigateToStoreDetail(
                    sideEffect.storeId
                )

                is ReportFinishSideEffect.NavigateToHome -> navigateToHome()
                is ReportFinishSideEffect.ShowSnackBar -> onShowSnackBar(
                    sideEffect.message,
                    sideEffect.jogboId
                )
            }
        }
    }

    ReportFinishScreen(
        count = state.count,
        name = state.name,
        storeName = state.storeName,
        storeId = state.storeId,
        showBottomSheet = state.showBottomSheet,
        jogboItems = state.jogboItems,
        onShowSnackBar = onShowSnackBar,
        addNewJogbo = {
            navigateToAddNewJogbo()
            viewModel.controlBottomSheetState(false)
        },
        bottomSheetControl = viewModel::controlBottomSheetState,
        moveToStoreDetail = viewModel::navigateToStoreDetail,
        moveToHome = viewModel::navigateToHome,
        addStoreAtJogbo = viewModel::addStoreAtJogbo,
    )
}

@Composable
fun ReportFinishScreen(
    count: Long,
    name: String,
    storeName: String,
    storeId: Long,
    showBottomSheet: Boolean,
    jogboItems: PersistentList<JogboResponseModel>,
    onShowSnackBar: (String, Long) -> Unit = { _, _ -> },
    addNewJogbo: () -> Unit = {},
    bottomSheetControl: (Boolean) -> Unit = { },
    moveToStoreDetail: () -> Unit = { },
    moveToHome: () -> Unit = { },
    addStoreAtJogbo: (Long, Long) -> Unit = { _, _ -> },
) {
    val localContextResource = LocalContext.current.resources

    if (showBottomSheet) {
        HankkiStoreJogboBottomSheet(
            jogboItems = jogboItems,
            addNewJogbo = addNewJogbo,
            onDismissRequest = { bottomSheetControl(false) },
            onAddJogbo = { jogboId ->
                addStoreAtJogbo(jogboId, storeId)
                onShowSnackBar(
                    localContextResource.getString(R.string.add_store_at_jogbo),
                    jogboId
                )
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_yellow_circle_gradient),
            contentDescription = "gradient",
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_store),
                contentDescription = "store",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }


        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_white_long_bottom_gradient),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(id = R.string.add_store, count),
                color = Gray900,
                style = HankkiTheme.typography.suitH2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text( // TODO: 이 부분은 랜덤 텍스트가 들어가고, 아직 확정나지 않아 하드코딩 해두겠습니다.
                text = "${name}님이 모두의 지갑을 지켰어요!",
                color = Gray500,
                style = HankkiTheme.typography.suitBody1
            )

            Spacer(modifier = Modifier.weight(3f))

            ReportFinishCard(
                storeName = storeName,
                onClick = { bottomSheetControl(true) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
            ) {
                HankkiButton(
                    text = stringResource(id = R.string.move_reported_store),
                    onClick = moveToStoreDetail,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                HankkiTextButton(
                    text = stringResource(id = R.string.to_home),
                    onClick = moveToHome,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReportFinishScreen() {
    HankkijogboTheme {
        ReportFinishScreen(
            count = 51,
            name = "정욱",
            storeName = "한끼네 한정식",
            storeId = 1,
            showBottomSheet = false,
            jogboItems = persistentListOf()
        )
    }
}
