package com.hankki.feature.storedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.ImageDoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.storedetail.component.StoreDetailReportButton

@Composable
fun StoreDetailReportRoute(
    storeId: Long,
    onNavigateUp: () -> Unit,
    viewModel: StoreDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.storeState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    when (dialogState) {
        StoreDetailDialogState.REPORT_CONFIRMATION -> {
            DoubleButtonDialog(
                title = "정말 제보하시겠어요?",
                description = "제보시 식당 정보가 앱에서 사라져요",
                negativeButtonTitle = "돌아가기",
                positiveButtonTitle = "제보하기",
                onNegativeButtonClicked = { viewModel.closeDialog() },
                onPositiveButtonClicked = {
                    viewModel.showThankYouDialog()
                    viewModel.deleteStoreDetail(storeId)
                }
            )
        }

        StoreDetailDialogState.REPORT -> {
            ImageDoubleButtonDialog(
                name = uiState.nickname,
                title = "변동사항을 알려주셔서 감사합니다 :)\n오늘도 저렴하고 든든한 식사하세요!",
                negativeButtonTitle = "",
                positiveButtonTitle = "돌아가기",
                onNegativeButtonClicked = {},
                onPositiveButtonClicked = {
                    viewModel.closeDialog()
                    onNavigateUp()
                }
            )
        }

        else -> {}
    }

    StoreDetailReportScreen(
        state = uiState,
        onNavigateUp = onNavigateUp,
        onSelectIndex = viewModel::updateSelectedIndex,
        onReportClicked = {
            viewModel.fetchNickname()
            viewModel.showReportConfirmation()
        }
    )
}

@Composable
private fun StoreDetailReportScreen(
    state: StoreDetailState,
    onNavigateUp: () -> Unit,
    onSelectIndex: (Int) -> Unit,
    onReportClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            HankkiTopBar(
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "뒤로가기",
                        modifier = Modifier
                            .offset(x = 6.dp, y = 2.dp)
                            .noRippleClickable(onClick = onNavigateUp),
                        tint = Gray900
                    )
                }
            )

            Spacer(modifier = Modifier.height(18.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "식당 정보가\n실제와 어떻게 다른가요?",
                    style = HankkiTheme.typography.suitH2,
                    color = Gray900,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 22.dp)
                )

                Spacer(modifier = Modifier.height(34.dp))
                state.buttonLabels.forEachIndexed { index, label ->
                    val isSelected = state.selectedIndex == index
                    StoreDetailReportButton(
                        content = {
                            Text(
                                text = label,
                                style = if (isSelected) {
                                    HankkiTheme.typography.body5.copy(color = Color.Red)
                                } else {
                                    HankkiTheme.typography.body6.copy(color = Gray400)
                                },
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, false)
                            )
                        },
                        onClick = {
                            if (state.selectedIndex == index) {
                                onSelectIndex(-1)
                            } else {
                                onSelectIndex(index)
                            }
                        },
                        tailingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = if (isSelected) R.drawable.ic_btn_radio_check
                                    else R.drawable.ic_btn_radio_uncheck
                                ),
                                contentDescription = "라디오 아이콘",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Unspecified
                            )
                        },
                        isSelected = isSelected
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "모두에게 보여지는 정보이니 신중하게 수정부탁드려요",
                        style = HankkiTheme.typography.suitBody3,
                        color = Gray400
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                HankkiButton(
                    text = "제보하기",
                    onClick = onReportClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    enabled = state.selectedIndex != -1
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
