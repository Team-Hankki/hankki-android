package com.hankki.feature.my.myjogbo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.amplitude.EventType
import com.hankki.core.common.amplitude.LocalTracker
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.layout.HankkiLoadingScreen
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R
import com.hankki.feature.my.myjogbo.component.AddJogboItem
import com.hankki.feature.my.myjogbo.component.JogboItem
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import com.hankki.feature.my.myjogbo.model.transformImage
import kotlinx.collections.immutable.PersistentList

@Composable
fun MyJogboRoute(
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: (Boolean) -> Unit,
    myJogboViewModel: MyJogboViewModel = hiltViewModel(),
) {
    val state by myJogboViewModel.myJogboState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myJogboViewModel.getMyJogboList()
    }

    MyJogboScreen(
        navigateUp = navigateUp,
        navigateToJogboDetail = navigateToJogboDetail,
        navigateToNewJogbo = navigateToNewJogbo,
        state = state.uiState,
        editModeState = state.editModeState,
        updateEditModeState = myJogboViewModel::updateEditModeState,
        updateJogboSelectedState = myJogboViewModel::updateJogboSeleted,
        resetEditModeState = myJogboViewModel::resetEditModeState,
        deleteDialogState = state.dialogState,
        updateDeleteDialogState = myJogboViewModel::updateDeleteDialogState,
        deleteSelectedJogbo = myJogboViewModel::deleteSelectedJogbo,
        buttonEnabledState = state.buttonEnabled
    )

    BackOnPressed(
        editMode = state.editModeState,
        resetJogboState = myJogboViewModel::resetEditModeState,
        navigateUp = navigateUp,
    )
}

@Composable
fun MyJogboScreen(
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: (Boolean) -> Unit,
    state: UiState<PersistentList<MyJogboModel>>,
    editModeState: Boolean,
    updateEditModeState: () -> Unit,
    updateJogboSelectedState: (Int, Boolean) -> Unit,
    resetEditModeState: () -> Unit,
    deleteDialogState: Boolean,
    updateDeleteDialogState: (Boolean) -> Unit,
    deleteSelectedJogbo: () -> Unit,
    buttonEnabledState: Boolean
) {
    val tracker = LocalTracker.current

    if (deleteDialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.ask_delete_jogbo),
            negativeButtonTitle = stringResource(id = R.string.close),
            positiveButtonTitle = stringResource(id = R.string.do_delete),
            onNegativeButtonClicked = { updateDeleteDialogState(false) },
            onPositiveButtonClicked = { if (buttonEnabledState) deleteSelectedJogbo() }
        )
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HankkiTopBar(
            leadingIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 7.dp)
                        .size(40.dp)
                        .noRippleClickable(if (editModeState) resetEditModeState else navigateUp),
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.my_jogbo),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            },
            trailingIcon = {
                val isSelectedJogboExists =
                    (state is UiState.Success && state.data.any { it.jogboSelected })

                Text(
                    text = if (editModeState) stringResource(R.string.delete) else stringResource(R.string.edit),
                    style = HankkiTheme.typography.body1,
                    color = Gray700,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .padding(end = 9.dp)
                        .run {
                            if (editModeState && isSelectedJogboExists) {
                                noRippleClickable {
                                    updateDeleteDialogState(true)
                                }
                            } else if (!editModeState) {
                                noRippleClickable(updateEditModeState)
                            } else {
                                this
                            }
                        }
                )
            }
        )

        when (state) {
            UiState.Failure -> {}
            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                ) {
                    HankkiLoadingScreen(modifier = Modifier.align(Alignment.Center))
                }
            }

            is UiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(top = 32.dp, bottom = 34.dp)
                ) {
                    item {
                        AddJogboItem(
                            isEditMode = editModeState,
                            onClick = {
                                tracker.track(
                                    type = EventType.CREATE,
                                    name = "Mypage_MyJokbo_NewJokbo"
                                )
                                navigateToNewJogbo(false)
                            }
                        )
                    }
                    itemsIndexed(state.data) { index, jogbo ->
                        JogboItem(
                            id = jogbo.jogboId,
                            title = jogbo.jogboName,
                            image = transformImage(jogbo.jogboImage),
                            isEditMode = editModeState,
                            isSelected = jogbo.jogboSelected,
                            editJogbo = { updateJogboSelectedState(index, !jogbo.jogboSelected) },
                            navigateToJogboDetail = { navigateToJogboDetail(jogbo.jogboId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BackOnPressed(
    editMode: Boolean = false,
    resetJogboState: () -> Unit,
    navigateUp: () -> Unit,
) {
    BackHandler(onBack = {
        if (editMode)
            resetJogboState()
        else
            navigateUp()
    })
}

@Composable
@Preview
fun MyJogboScreenPreview() {
    HankkijogboTheme {
        MyJogboScreen(
            navigateUp = {},
            navigateToJogboDetail = { _ -> },
            navigateToNewJogbo = {},
            state = UiState.Loading,
            updateEditModeState = {},
            updateJogboSelectedState = { _, _ -> },
            resetEditModeState = {},
            deleteDialogState = false,
            updateDeleteDialogState = {},
            deleteSelectedJogbo = {},
            buttonEnabledState = false,
            editModeState = false
        )
    }
}

