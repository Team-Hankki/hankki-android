package com.hankki.feature.report.searchstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.extension.addFocusCleaner
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.EmptyUiState
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.BottomBlurLayout
import com.hankki.core.designsystem.component.layout.EmptyView
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.layout.TopBlurLayout
import com.hankki.core.designsystem.component.textfield.HankkiSearchTextField
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.searchstore.component.EmptyLocationView
import com.hankki.feature.report.searchstore.component.LocationList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchStoreRoute(
    navigateReport: (
        latitude: String,
        longitude: String,
        location: String,
        address: String,
    ) -> Unit,
    navigateToStoreDetail: (storeId: Long) -> Unit,
    navigateUp: () -> Unit,
    viewModel: SearchStoreViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val value by viewModel.value.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { sideEffect ->
            when (sideEffect) {
                is SearchStoreSideEffect.ValidateUniversitySuccess -> {
                    navigateReport(
                        sideEffect.latitude,
                        sideEffect.longitude,
                        sideEffect.location,
                        sideEffect.address
                    )
                }

                is SearchStoreSideEffect.NavigateToStoreDetail -> navigateToStoreDetail(sideEffect.storeId)
            }
        }
    }

    SearchStoreScreen(
        value = value,
        selectedLocation = state.selectedLocation,
        isOpenDialog = state.isOpenDialog,
        dialogState = state.dialogState,
        state = state.uiState,
        onValueChange = viewModel::setValue,
        onClickLocation = viewModel::setLocation,
        navigateUp = navigateUp,
        navigateToStoreDetail = viewModel::navigateToStoreDetail,
        postUniversityStore = viewModel::postUniversityStore,
        reportButtonClicked = viewModel::reportButtonClicked,
        onDismissDialog = { viewModel.setDialogState(false) }
    )
}

@Composable
fun SearchStoreScreen(
    value: String,
    selectedLocation: LocationModel,
    isOpenDialog: Boolean,
    dialogState: DialogState,
    state: EmptyUiState<PersistentList<LocationModel>>,
    onValueChange: (String) -> Unit = {},
    onClickLocation: (LocationModel) -> Unit = {},
    navigateUp: () -> Unit = {},
    navigateToStoreDetail: (storeId: Long) -> Unit = {},
    postUniversityStore: (storeId: Long) -> Unit = {},
    reportButtonClicked: () -> Unit = {},
    onDismissDialog: () -> Unit = {},
) {
    val tracker = LocalTracker.current
    val focusManager = LocalFocusManager.current

    if (isOpenDialog) {
        val (title, negativeButtonTitle, positiveButtonTitle) = when (dialogState) {
            is DialogState.MySchool -> {
                Triple(
                    "등록된 식당이 있어요\n식당으로 이동할까요?",
                    "아니요",
                    "이동하기"
                )
            }

            is DialogState.OtherSchool -> {
                Triple(
                    "다른학교에 제보된 식당이에요\n우리학교에도 추가할까요?",
                    "아니요",
                    "추가하기"
                )
            }

            else -> Triple("오류가 발생했어요", "", "돌아가기")
        }

        DoubleButtonDialog(
            title = title,
            negativeButtonTitle = negativeButtonTitle,
            positiveButtonTitle = positiveButtonTitle,
            onNegativeButtonClicked = onDismissDialog,
            onPositiveButtonClicked = {
                when (dialogState) {
                    is DialogState.MySchool -> {
                        navigateToStoreDetail(dialogState.storeId)
                    }

                    is DialogState.OtherSchool -> {
                        postUniversityStore(dialogState.storeId)
                    }

                    else -> {
                        onDismissDialog()
                    }
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .background(White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        HankkiTopBar(
            modifier = Modifier.background(White),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(navigateUp)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 22.dp)
        ) {
            Text(
                text = stringResource(id = com.hankki.feature.report.R.string.search_store_sub_title),
                style = HankkiTheme.typography.suitH2,
                color = Gray900
            )
            Spacer(modifier = Modifier.height(18.dp))
            HankkiSearchTextField(
                value = value,
                onTextChanged = onValueChange,
                clearText = { onValueChange("") }
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                EmptyUiState.Empty -> {
                    EmptyLocationView(text = value)
                }

                EmptyUiState.Failure -> {
                    EmptyView(text = stringResource(R.string.error_text))
                }

                EmptyUiState.Loading -> {
                    HankkiLoadingScreen(modifier = Modifier
                        .fillMaxHeight(0.7f)
                        .fillMaxWidth())
                }

                is EmptyUiState.Success -> LocationList(
                    selectedLocation = selectedLocation,
                    locations = state.data,
                    onClick = {
                        onClickLocation(it)
                        focusManager.clearFocus()
                    }
                )
            }

            TopBlurLayout(
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Box(
                modifier = Modifier.align(Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomBlurLayout()
                HankkiButton(
                    text = stringResource(
                        id = if (selectedLocation.location.isEmpty()) com.hankki.feature.report.R.string.choose_store
                        else com.hankki.feature.report.R.string.report_this_store
                    ),
                    onClick = {
                        reportButtonClicked()
                        tracker.track(
                            type = EventType.CLICK,
                            name = "Report_RestaurantComplete"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 15.dp),
                    enabled = selectedLocation.location.isNotEmpty()
                )
            }
        }

    }
}

internal class RoomPreviewParameterProvider :
    PreviewParameterProvider<EmptyUiState<PersistentList<LocationModel>>> {
    override val values = sequenceOf(
        EmptyUiState.Empty,
        EmptyUiState.Loading,
        EmptyUiState.Failure,
        EmptyUiState.Success(
            persistentListOf(
                LocationModel(
                    location = "고동밥집 1호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = "0.0",
                    latitude = "0.0"
                ),
                LocationModel(
                    location = "고동밥집 2호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = "0.0",
                    latitude = "0.0"
                ),
                LocationModel(
                    location = "고동밥집 3호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = "0.0",
                    latitude = "0.0"
                ),
                LocationModel(
                    location = "고동밥집 4호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = "0.0",
                    latitude = "0.0"
                ),
                LocationModel(
                    location = "고동밥집 5호점",
                    address = "서울특별시 마포구 갈매기 고양이처럼 울음 ",
                    longitude = "0.0",
                    latitude = "0.0"
                ),
            )
        )
    )
}

@Preview
@Composable
private fun BookmarkCardPreview(
    @PreviewParameter(RoomPreviewParameterProvider::class) state: EmptyUiState<PersistentList<LocationModel>>,
) {
    HankkijogboTheme {
        SearchStoreScreen(
            "고동밥집",
            selectedLocation = LocationModel(),
            isOpenDialog = false,
            state = state,
            onValueChange = {},
            onClickLocation = { },
            navigateUp = {},
            reportButtonClicked = {},
            onDismissDialog = {},
            dialogState = DialogState.None,
            navigateToStoreDetail = { }
        )
    }
}
