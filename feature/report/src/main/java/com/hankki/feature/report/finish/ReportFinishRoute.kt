package com.hankki.feature.report.finish

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.HankkiTextButton
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.report.R
import com.hankki.feature.report.finish.component.ReportFinishCard

@Composable
fun ReportFinishRoute(
    viewModel: ReportFinishViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ReportFinishScreen(
        count = state.count,
        name = state.name,
        storeName = state.storeName,
        addMyJogbo = viewModel::addMyJogbo
    )
}

@Composable
fun ReportFinishScreen(
    count: Int,
    name: String,
    storeName: String,
    addMyJogbo: () -> Unit = { },
    moveToStoreDetail: () -> Unit = { },
    moveToHome: () -> Unit = { },
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                text = "${count}번째 식당을 등록했어요",
                color = Gray900,
                style = HankkiTheme.typography.suitH2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${name}님이 모두의 지갑을 지켰어요!",
                color = Gray500,
                style = HankkiTheme.typography.suitBody1
            )

            Spacer(modifier = Modifier.weight(3f))

            ReportFinishCard(
                storeName = storeName,
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
            ) {
                HankkiButton(
                    text = "제보한 식당 보러가기",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                HankkiTextButton(
                    text = "홈으로",
                    onClick = { /*TODO*/ },
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
        ReportFinishScreen(count = 51, name = "정욱", storeName = "한끼네 한정식")
    }
}
